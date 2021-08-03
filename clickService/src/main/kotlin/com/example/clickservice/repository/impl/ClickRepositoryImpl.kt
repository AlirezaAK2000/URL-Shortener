package com.example.clickservice.repository.impl

import com.example.clickservice.repository.entity.Click
import com.example.clickservice.repository.extra.ClickRepositoryExtra
import com.example.clickservice.service.model.aggregation.id.ClickPerDayPopulationId
import com.example.clickservice.service.model.aggregation.id.ClickPerHourPopulationId
import com.example.clickservice.service.model.aggregation.population.ClickPerDayPopulation
import com.example.clickservice.service.model.aggregation.population.ClickPerHourPopulation
import com.example.clickservice.service.model.aggregation.population.ClickPopulation
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Repository
import java.util.*


@Repository
class ClickRepositoryImpl(
    val mongoTemplate: MongoTemplate
) : ClickRepositoryExtra {

    companion object {
        const val YEAR_OPERATOR = "year"
        const val DAYOFYEAR_OPERATOR = "dayOfYear"
        const val HOUR_OPERATOR = "hour"
    }

    override fun findAllURLClickCountByDate(
        startDate: Date,
        endDate: Date
    ): AggregationResults<ClickPopulation> {

        val criteria = Criteria().andOperator(Criteria.where(Click.CLICK_TIME).gte(startDate).lte(endDate))
        val matcherStage = Aggregation.match(criteria)
        val groupStage = Aggregation.group(Click.URL_ID).count().`as`(ClickPopulation.COUNT)
        val sortStage = Aggregation.sort(Sort.by(Sort.Direction.DESC, ClickPopulation.COUNT))
        val aggregation = Aggregation.newAggregation(
            matcherStage,
            groupStage,
            sortStage
        )

        return mongoTemplate.aggregate(
            aggregation, Click.COLLECTION_NAME, ClickPopulation::class.java
        )
    }

    override fun findAllURLClicksCountPerHour(): AggregationResults<ClickPerHourPopulation> =
        findAllURLClicksCountPerHourFinalStages(arrayListOf())

    override fun findAllURLClicksCountPerDay(): AggregationResults<ClickPerDayPopulation> =
        findAllURLClicksCountPerDayFinalStages(arrayListOf())

    override fun findAllURLClicksCountPerHourByURLId(URLId: String): AggregationResults<ClickPerHourPopulation> {
        val matchStage = Aggregation.match(
            Criteria.where(Click.URL_ID).`is`(URLId)
        )
        return findAllURLClicksCountPerHourFinalStages(arrayListOf(matchStage))
    }

    override fun findAllURLClicksCountPerDayByURLId(URLId: String): AggregationResults<ClickPerDayPopulation> {
        val matchStage = Aggregation.match(
            Criteria.where(Click.URL_ID).`is`(URLId)
        )
        return findAllURLClicksCountPerDayFinalStages(arrayListOf(matchStage))
    }

    private fun findAllURLClicksCountPerHourFinalStages(pipeline: MutableList<AggregationOperation>): AggregationResults<ClickPerHourPopulation> {
        val projectStage = Aggregation.project(Click.CLICK_TIME)
            .andExpression("$YEAR_OPERATOR(${Click.CLICK_TIME})").`as`(ClickPerHourPopulationId.YEAR)
            .andExpression("$DAYOFYEAR_OPERATOR(${Click.CLICK_TIME})").`as`(ClickPerHourPopulationId.DAY)
            .andExpression(("$HOUR_OPERATOR(${Click.CLICK_TIME})")).`as`(ClickPerHourPopulationId.HOUR)
        pipeline.add(projectStage)

        val groupStage = Aggregation.group(
            ClickPerHourPopulationId.YEAR,
            ClickPerHourPopulationId.DAY,
            ClickPerHourPopulationId.HOUR
        ).count().`as`(ClickPerHourPopulation.COUNT)
        pipeline.add(groupStage)

        val sortStage = Aggregation.sort(
            Sort.by(
                Sort.Direction.DESC,
                "_id.${ClickPerHourPopulationId.YEAR}",
                "_id.${ClickPerHourPopulationId.DAY}",
                "_id.${ClickPerHourPopulationId.HOUR}"
            )
        )
        pipeline.add(sortStage)

        val aggregation = Aggregation.newAggregation(
            pipeline
        )

        return mongoTemplate.aggregate(
            aggregation,
            Click.COLLECTION_NAME,
            ClickPerHourPopulation::class.java
        )
    }


    private fun findAllURLClicksCountPerDayFinalStages(pipeline: MutableList<AggregationOperation>): AggregationResults<ClickPerDayPopulation> {

        val projectStage = Aggregation.project(Click.CLICK_TIME)
            .andExpression("$YEAR_OPERATOR(${Click.CLICK_TIME})").`as`(ClickPerDayPopulationId.YEAR)
            .andExpression("$DAYOFYEAR_OPERATOR(${Click.CLICK_TIME})").`as`(ClickPerDayPopulationId.DAY)
        pipeline.add(projectStage)

        val groupStage = Aggregation.group(
            ClickPerDayPopulationId.YEAR,
            ClickPerDayPopulationId.DAY
        ).count().`as`(ClickPerDayPopulation.COUNT)
        pipeline.add(groupStage)

        val sortStage = Aggregation.sort(
            Sort.by(
                Sort.Direction.DESC,
                "_id.${ClickPerHourPopulationId.YEAR}",
                "_id.${ClickPerHourPopulationId.DAY}"
            )
        )
        pipeline.add(sortStage)

        val aggregation = Aggregation.newAggregation(
            pipeline
        )

        return mongoTemplate.aggregate(
            aggregation,
            Click.COLLECTION_NAME,
            ClickPerDayPopulation::class.java
        )

    }

}