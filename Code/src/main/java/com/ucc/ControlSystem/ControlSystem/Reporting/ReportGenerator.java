package com.ucc.ControlSystem.ControlSystem.Reporting;

import com.ucc.ControlSystem.ControlSystem.JDBC.HSQLQueries;
import com.ucc.ControlSystem.SimulationEnvironment.EnvironmentDeviceTypes;
import com.ucc.ControlSystem.Utils.TimeConvertor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ReportGenerator {

    private static ReportGenerator reportGenerator = null;

    private ReportGenerator(){}

    public static ReportGenerator getReportGenerator(){
        if(reportGenerator == null){
            reportGenerator = new ReportGenerator();
        }
        return reportGenerator;
    }

    public void generateReport(int dayNo){
        List<Measurement> resultList = getReportsBasedOnTypeOnDay(EnvironmentDeviceTypes.AIR_TEMPERATURE,dayNo);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(resultList);

        Map parameters = new HashMap<>();

        String sourceFileName = "src/main/resources/ReportTemplates/temp1.jrxml";

        try {
            InputStream input = new FileInputStream(new File(sourceFileName));
            JasperDesign jasperDesign = JRXmlLoader.load(input);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,dataSource);

                File pdfResult = File.createTempFile("/ReportForDay_"+dayNo,".pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint,new FileOutputStream(pdfResult));
//            JasperViewer.viewReport(jasperPrint);
        } catch ( JRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Measurement> getReportsBasedOnTypeOnDay(EnvironmentDeviceTypes type, int dayNo){
        long startTime = TimeConvertor.getDayInSeconds(dayNo);
        long endTime = startTime + TimeConvertor.getDayInSeconds(1);

        return HSQLQueries.getHSQLQueries().getMeasurementsBasedOnTypeAndInterval(type,startTime,endTime);
    }

}
