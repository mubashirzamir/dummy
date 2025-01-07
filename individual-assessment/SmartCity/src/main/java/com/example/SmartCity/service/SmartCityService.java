package com.example.SmartCity.service;

import com.example.SmartCity.client.ElectricalProviderClient;
import com.example.SmartCity.dto.*;
import com.example.SmartCity.model.ElectricalProviderConsumptionSummary;
import com.example.SmartCity.repository.SmartCityRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service class for handling business logic related to smart city operations.
 */
@Service
public class SmartCityService {

    private final SmartCityRepository smartCityRepository;


    private final ElectricalProviderClient electricalProviderClient;

    /**
     * Constructor for SmartCityService.
     *
     * @param repository the repository to handle data persistence
     * @param electricalProviderClient the client to fetch data from the electrical provider
     */
    public SmartCityService(SmartCityRepository repository , ElectricalProviderClient electricalProviderClient) {
        this.smartCityRepository = repository;
        this.electricalProviderClient = electricalProviderClient;
    }

    /**
     * Retrieves the summary of an individual electrical provider.
     *
     * @param providerId the ID of the electrical provider
     * @return a list of ElectricalProviderConsumptionSummary objects
     * @throws IllegalArgumentException if no data is found for the given provider ID
     */
    public List<ElectricalProviderConsumptionSummary> viewIndividualProviderSummary(String providerId) {
        return smartCityRepository.getCitizenProviderSummaryByProviderId(providerId).
                orElseThrow(() -> new IllegalArgumentException("No data found for provider ID: " + providerId));
    }

    /**
     * Fetches and saves the electrical provider consumption summary.
     *
     * @param providerId the ID of the electrical provider
     * @return the saved ElectricalProviderConsumptionSummary object
     * @throws IllegalArgumentException if no data is found or data already exists for the current month
     */
    public ElectricalProviderConsumptionSummary fetchAndSaveElectricalProviderSummary(String providerId) {
        ElectricalProviderConsumptionSummary summary =electricalProviderClient.getSummaryElectricalProviderSmartMeterData(providerId);

        // Check if the summary is null and throw an error if no data is found
        Date summaryDate = summary.getDate();
        System.out.println("summaryDate: "+summaryDate);
        if (summaryDate == null) {
            throw new IllegalArgumentException("Summary date is null for provider ID: " + providerId);
        }
        // Fetch the latest record for the given provider ID
        ElectricalProviderConsumptionSummary latestSummary;
        try {
            latestSummary = smartCityRepository.findTopByProviderIdOrderByDateDesc(providerId)
                    .orElseThrow(() -> new IllegalArgumentException("No data found for provider ID: " + providerId));
        } catch (IllegalArgumentException e) {
            // If no data is found, save the new summary
            ElectricalProviderConsumptionSummary saveSummary = new ElectricalProviderConsumptionSummary();
            saveSummary.setId(new ObjectId().toString());
            saveSummary.setProviderId(summary.getProviderId());
            saveSummary.setTotalMonthlyConsumption(summary.getTotalMonthlyConsumption());
            saveSummary.setDailyAverageConsumption(summary.getDailyAverageConsumption());
            saveSummary.setAverageConsumptionPerCitizen(summary.getAverageConsumptionPerCitizen());
            saveSummary.setPeakHourlyConsumption(summary.getPeakHourlyConsumption());
            saveSummary.setCitizenCount(summary.getCitizenCount());
            saveSummary.setDate(summary.getDate());
            smartCityRepository.save(saveSummary);
            return saveSummary;
        }
        // Check if the latest data is for the current month
        if (latestSummary != null && isSameMonth(latestSummary.getDate(), new Date())) {
            throw new IllegalArgumentException("Data already exists for the current month. Please call Put instead.");
        }
        ElectricalProviderConsumptionSummary saveSummary = new ElectricalProviderConsumptionSummary();
        saveSummary.setId(new ObjectId().toString());
        saveSummary.setProviderId(summary.getProviderId());
        saveSummary.setTotalMonthlyConsumption(summary.getTotalMonthlyConsumption());
        saveSummary.setDailyAverageConsumption(summary.getDailyAverageConsumption());
        saveSummary.setAverageConsumptionPerCitizen(summary.getAverageConsumptionPerCitizen());
        saveSummary.setPeakHourlyConsumption(summary.getPeakHourlyConsumption());
        saveSummary.setCitizenCount(summary.getCitizenCount());
        saveSummary.setDate(summary.getDate());
        smartCityRepository.save(saveSummary);
        return saveSummary;
    }

    public List<ElectricalProviderConsumptionSummary> fetchAndSaveAllElectricalProviderSummary(String providerId) {
        List<ElectricalProviderConsumptionSummary> savedSummaries = new ArrayList<>();
        List<ElectricalProviderConsumptionSummary> summaries = electricalProviderClient.getAllSummaryElectricalProviderSmartMeterData(providerId);
        for (ElectricalProviderConsumptionSummary summary : summaries) {
            // Check if the summary is null and throw an error if no data is found
            Date summaryDate = summary.getDate();
            System.out.println("summaryDate: " + summaryDate);
            if (summaryDate == null) {
                throw new IllegalArgumentException("Summary date is null for provider ID: " + summary.getProviderId());
            }

            // Fetch the latest record for the given provider ID
            ElectricalProviderConsumptionSummary latestSummary;
            try {
                latestSummary = smartCityRepository.findTopByProviderIdOrderByDateDesc(summary.getProviderId())
                        .orElse(null);
            } catch (Exception e) {
                latestSummary = null;
            }

            // If data exists for the current month, update it
            if (latestSummary != null && isSameMonth(latestSummary.getDate(), new Date())) {
                latestSummary.setTotalMonthlyConsumption(summary.getTotalMonthlyConsumption());
                latestSummary.setDailyAverageConsumption(summary.getDailyAverageConsumption());
                latestSummary.setAverageConsumptionPerCitizen(summary.getAverageConsumptionPerCitizen());
                latestSummary.setPeakHourlyConsumption(summary.getPeakHourlyConsumption());
                latestSummary.setCitizenCount(summary.getCitizenCount());
                latestSummary.setDate(summary.getDate());
                smartCityRepository.save(latestSummary);
                savedSummaries.add(latestSummary);
            } else {
                // Save the new summary
                ElectricalProviderConsumptionSummary saveSummary = new ElectricalProviderConsumptionSummary();
                saveSummary.setId(new ObjectId().toString());
                saveSummary.setProviderId(summary.getProviderId());
                saveSummary.setTotalMonthlyConsumption(summary.getTotalMonthlyConsumption());
                saveSummary.setDailyAverageConsumption(summary.getDailyAverageConsumption());
                saveSummary.setAverageConsumptionPerCitizen(summary.getAverageConsumptionPerCitizen());
                saveSummary.setPeakHourlyConsumption(summary.getPeakHourlyConsumption());
                saveSummary.setCitizenCount(summary.getCitizenCount());
                saveSummary.setDate(summary.getDate());
                smartCityRepository.save(saveSummary);
                savedSummaries.add(saveSummary);
            }
        }

        return savedSummaries;
    }

    /**
     * Updates the electrical provider consumption summary.
     *
     * @param providerId the ID of the electrical provider
     * @return the updated ElectricalProviderConsumptionSummary object
     * @throws IllegalArgumentException if no data is found or fetched data does not correspond to the current month
     */
    public ElectricalProviderConsumptionSummary updateElectricalProviderSummary(String providerId) {
        // Fetch the latest record for the given provider ID
        ElectricalProviderConsumptionSummary latestSummary = smartCityRepository.findTopByProviderIdOrderByDateDesc(providerId)
                .orElseThrow(() -> new IllegalArgumentException("No data found for provider ID: " + providerId));

        // Fetch new data from the electricalProviderSummary microservice
        ElectricalProviderConsumptionSummary fetchedSummary = electricalProviderClient.getSummaryElectricalProviderSmartMeterData(providerId);

        // Check if the latest data is for the current month
        Date now = new Date();
        if (isSameMonth(latestSummary.getDate(), now)) {


            // Validate that the fetched data corresponds to the new month
            if (isSameMonth(fetchedSummary.getDate(), now)) {
                throw new IllegalArgumentException("Fetched data does not correspond to the current month. Please call Post instead.");
            }

            // Save the fetched data as the new summary
            return smartCityRepository.save(fetchedSummary);
        }


        // Update data for the current month
        latestSummary.setDate(now);
        latestSummary.setProviderId(fetchedSummary.getProviderId());
        latestSummary.setTotalMonthlyConsumption(fetchedSummary.getTotalMonthlyConsumption());
        latestSummary.setDailyAverageConsumption(fetchedSummary.getDailyAverageConsumption());
        latestSummary.setAverageConsumptionPerCitizen(fetchedSummary.getAverageConsumptionPerCitizen());
        latestSummary.setPeakHourlyConsumption(fetchedSummary.getPeakHourlyConsumption());

        // Save updated data
        return smartCityRepository.save(latestSummary);
    }

    /**
     * Checks if two LocalDateTime objects are in the same month and year.
     *
     * @param timestamp the first LocalDateTime object
     * @param now the second LocalDateTime object
     * @return true if both timestamps are in the same month and year, false otherwise
     */
    private boolean isSameMonth(Date timestamp, Date now) {
        return timestamp.getYear() == now.getYear() && timestamp.getMonth() == now.getMonth();
    }

    //Created for sake of part 2 of project

    private Date[] calculateTimeRange(String timeRange) {
        LocalDateTime now = LocalDateTime.now();
        Map<String, LocalDateTime[]> timeRanges = Map.of(
                "LAST_24_HOURS", new LocalDateTime[]{now.minusDays(1), now},
                "LAST_7_DAYS", new LocalDateTime[]{now.minusDays(7), now},
                "LAST_30_DAYS", new LocalDateTime[]{now.minusDays(30), now}
        );

        LocalDateTime[] range = timeRanges.getOrDefault(timeRange, null);
        if (range != null) {
            return new Date[]{
                    Date.from(range[0].atZone(ZoneId.systemDefault()).toInstant()),
                    Date.from(range[1].atZone(ZoneId.systemDefault()).toInstant())
            };
        }
        return null;
    }

    public List<ElectricalProviderConsumptionSummary> getAllCitySummary(){
        return smartCityRepository.findAll();
    }

    public List<ElectricalProviderConsumptionSummary> getDataSummaryByDate(String date) {
        Date[] range = calculateTimeRange(date);
        if (range == null) {
            throw new IllegalArgumentException("Invalid time range");
        }
        return smartCityRepository.findByDate(range[0], range[1]);
    }

    public List<ElectricalProviderModel> getProviderInfo (){
        List<ElectricalProviderModel> data= electricalProviderClient.getAllElectricalProviderInfo();
        return data;
    }

    public List<ConsumptionByProviderSummary> getAggregatedByProvider(String timeRange) {
        Date[] range = calculateTimeRange(timeRange);
        if (range == null) {
            throw new IllegalArgumentException("Invalid time range");
        }
        return smartCityRepository.AggregatedByProviderByDate(range[0], range[1]);
    }

    public ConsumptionForCitySummary getAggregatedForCity(String timeRange) {
        Date[] range = calculateTimeRange(timeRange);
        if (range == null) {
            throw new IllegalArgumentException("Invalid time range");
        }
        return smartCityRepository.findAggregatedForCity(range[0], range[1]);
    }

    public List<MonthlyAverageByProviderSummary> getMonthlyAverageByProvider(int year) {
        if (year < 1900 || year > LocalDateTime.now().getYear()) {
            throw new IllegalArgumentException("Invalid year specified.");
        }
        return smartCityRepository.findMonthlyAverageByProvider(year);
    }

    public List<MonthlyAverageForCitySummary> getMonthlyAverageForCity(int year) {
        if (year < 1900 || year > LocalDateTime.now().getYear()) {
            throw new IllegalArgumentException("Invalid year specified.");
        }
        return smartCityRepository.findMonthlyAverageForCity(year);
    }

}