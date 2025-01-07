package com.example.SmartCity.repository;

import com.example.SmartCity.dto.ConsumptionByProviderSummary;
import com.example.SmartCity.dto.ConsumptionForCitySummary;
import com.example.SmartCity.dto.MonthlyAverageByProviderSummary;
import com.example.SmartCity.dto.MonthlyAverageForCitySummary;
import com.example.SmartCity.model.ElectricalProviderConsumptionSummary;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SmartCityRepository extends MongoRepository<ElectricalProviderConsumptionSummary, String> {

    Optional<ElectricalProviderConsumptionSummary> findTopByProviderIdOrderByDateDesc(String providerId);

    Optional<List<ElectricalProviderConsumptionSummary>> getCitizenProviderSummaryByProviderId(String providerId);


    @Query("{'Date': { $gte: ?0, $lte: ?1 }}")
    List<ElectricalProviderConsumptionSummary> findByDate(Date start, Date end);

    @Aggregation(pipeline = {
            "{ $match: { Date: { $gte: ?0, $lte: ?1 } } }",
            "{ $group: { " +
                    "    _id: { providerId: { $ifNull: ['$ProviderId', 'Unknown'] } }, " +
                    "    totalConsumption: { $sum: '$TotalMonthlyConsumption' }, " +
                    "    averageConsumption: { $avg: '$TotalMonthlyConsumption' } " +
                    "} }",
            "{ $project: { " +
                    "    _id: 0, " +
                    "    providerId: '$_id.providerId', " +
                    "    totalConsumption: 1, " +
                    "    averageConsumption: 1 " +
                    "} }"
    })
    List<ConsumptionByProviderSummary> AggregatedByProviderByDate(Date start, Date end);

    @Aggregation(pipeline = {
            "{ $match: { Date: { $gte: ?0, $lte: ?1 } } }",
            "{ $project: { " +
                    "    totalPerCitizen: { $divide: ['$TotalMonthlyConsumption', '$CitizenCount'] }, " +
                    "    TotalMonthlyConsumption: 1 " +
                    "} }",
            "{ $group: { " +
                    "    _id: null, " +
                    "    totalConsumption: { $sum: '$TotalMonthlyConsumption' }, " +
                    "    averageConsumption: { $sum: '$totalPerCitizen' } " +
                    "} }",
            "{ $project: { " +
                    "    _id: 0, " +
                    "    totalConsumption: 1, " +
                    "    averageConsumption: 1 " +
                    "} }"
    })
    ConsumptionForCitySummary findAggregatedForCity(Date startDate, Date endDate);

    @Aggregation(pipeline = {
            "{ $match: { $expr: { $eq: [ { $year: '$Date' }, ?0 ] } } }",
            "{ $group: { " +
                    "    _id: { ProviderId: { $ifNull: ['$ProviderId', 'Unknown'] }, month: { $month: '$Date' } }, " +
                    "    totalConsumption: { $sum: '$TotalMonthlyConsumption' }, " +
                    "    totalCitizens: { $sum: '$CitizenCount' } " +
                    "} }",
            "{ $project: { " +
                    "    _id: 0, " +
                    "    ProviderId: '$_id.ProviderId', " +
                    "    month: '$_id.month', " +
                    "    averageConsumption: { $divide: ['$totalConsumption', '$totalCitizens'] } " +
                    "} }"
    })
    List<MonthlyAverageByProviderSummary> findMonthlyAverageByProvider(int year);


    @Aggregation(pipeline = {
            "{ $match: { $expr: { $eq: [ { $year: '$Date' }, ?0 ] } } }",
            "{ $group: { " +
                    "    _id: { month: { $month: '$Date' } }, " +
                    "    totalConsumption: { $sum: '$TotalMonthlyConsumption' }, " +
                    "    totalCitizens: { $sum: '$CitizenCount' } " +
                    "} }",
            "{ $project: { " +
                    "    _id: 0, " +
                    "    month: '$_id.month', " +
                    "    averageConsumption: { $divide: ['$totalConsumption', '$totalCitizens'] } " +
                    "} }"
    })
    List<MonthlyAverageForCitySummary> findMonthlyAverageForCity(int year);

}

