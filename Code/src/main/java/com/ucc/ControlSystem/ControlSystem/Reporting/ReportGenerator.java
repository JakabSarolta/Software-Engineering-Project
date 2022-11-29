package com.ucc.ControlSystem.ControlSystem.Reporting;

import com.ucc.ControlSystem.ControlSystem.JDBC.HSQLQueries;
import com.ucc.ControlSystem.EnvironmentSimulator.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.Utils.TimeConvertor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ReportGenerator {

    private static ReportGenerator reportGenerator = null;

    private static String OUTPUT_DIRECTORY = ".\\src\\main\\resources\\GeneratedReports\\";
    private static String INPUT_TEMPLATE = "src/main/resources/ReportTemplates/temp1.jrxml";

    private ReportGenerator(){}

    public static ReportGenerator getReportGenerator(){
        if(reportGenerator == null){
            reportGenerator = new ReportGenerator();
        }
        return reportGenerator;
    }

    public void generateReport(int startDayNo, int endDayNo, List<EnvironmentDeviceTypes> selectedSensors){
        List<List<Measurement>> resultList = getMeasurementsInIntervalForDevices(startDayNo,endDayNo,selectedSensors);

        Map<String,Integer> parameters = new HashMap<>();
        parameters.put("intervalStart",startDayNo);
        parameters.put("intervalEnd",endDayNo);

        for(List<Measurement> resultForDevice : resultList){
            writePDF(resultForDevice,OUTPUT_DIRECTORY + "Report_for_"+resultForDevice.get(0).getDevice()+
                    "_days_"+startDayNo+"_to_"+endDayNo+".pdf",parameters);
        }
    }

    private void writePDF(List<Measurement> resultList,String fileName, Map parameters){
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        try {
            InputStream input = new FileInputStream(INPUT_TEMPLATE);
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,dataSource);

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));

            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        } catch ( JRException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private List<List<Measurement>> getMeasurementsInIntervalForDevices(int startDay, int endDay, List<EnvironmentDeviceTypes> devices){
        long startSeconds = convertDayNoToSeconds(startDay);
        long endSeconds = convertDayNoToSeconds(endDay+1)-1;

        return devices.stream()
                .map(deviceType ->
                        HSQLQueries.getHSQLQueries().getMeasurementsBasedOnTypeAndInterval(deviceType,startSeconds,endSeconds))
                .toList();
    }

    private long convertDayNoToSeconds(int dayNo){
        return dayNo * 86400L;
    }
}
