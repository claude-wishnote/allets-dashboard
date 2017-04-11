package com.allets.backend.data.server.utils;

import com.allets.backend.data.server.data.result.ContentsStatistics;
import com.allets.backend.data.server.data.result.TimeLineStatistics;
import com.allets.backend.data.server.data.result.StatisticsListsHolder;
import com.mysema.util.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by claude on 2016/2/27.
 */
public class ExcelUtil {
    public static HSSFWorkbook createStatisticsExcel(StatisticsListsHolder statisticsListsHolder, String filePath) throws Exception {
        File excelFile = null;

        HSSFWorkbook wb = new HSSFWorkbook();
        wb = createContentsStatisticsSheet(wb, statisticsListsHolder);
        wb = createStatisticsListsSheet(wb, statisticsListsHolder);
        wb = createTimeLineStatusticsSheet(wb, statisticsListsHolder);
        return wb;
    }

    static HSSFWorkbook createStatisticsListsSheet(HSSFWorkbook wb, StatisticsListsHolder statisticsListsHolder) {
        String title = statisticsListsHolder.getTableTitlesList().get(0);
        String sheetName = statisticsListsHolder.getSheetNameList().get(1);
        Integer LineSign = 0;
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setColumnWidth(0, 40 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        ;
        HSSFRow row = sheet.createRow(LineSign);
        row.setHeight((short) 600);
        HSSFCell titleCell = row.createCell(0);
        titleCell.setCellValue(title);
        HSSFCellStyle alignCenterStyle = wb.createCellStyle();
        alignCenterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        titleCell.setCellStyle(alignCenterStyle);

        LineSign = LineSign + 1;
        row = sheet.createRow(LineSign);
        alignCenterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCell cell = row.createCell(0);
        row.setHeight((short) 600);
        cell.setCellValue("Report type");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(1);
        cell.setCellValue("Deleted count");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(2);
        cell.setCellValue("Reported count");
        cell.setCellStyle(alignCenterStyle);
//        Integer totalDeleteCount = 0;
        Integer totalReportCount = 0;
        for (int i = 0; i < statisticsListsHolder.getReportTypeStringList().size(); i++) {
            LineSign = LineSign + 1;
            row = sheet.createRow(LineSign);
            row.setRowStyle(alignCenterStyle);
            HSSFCell eachCell = row.createCell(0);
            eachCell.setCellValue(statisticsListsHolder.getReportTypeStringList().get(i));
            eachCell = row.createCell(1);
            eachCell.setCellValue(statisticsListsHolder.getDeleteCommentCountForEachReportType().get(i));
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(2);
            eachCell.setCellValue(statisticsListsHolder.getReportCountForEachReportType().get(i));
            eachCell.setCellStyle(alignCenterStyle);
//            totalDeleteCount = totalDeleteCount + statisticsListsHolder.getDeleteCommentCountForEachReportType().get(i);
            totalReportCount = totalReportCount + statisticsListsHolder.getReportCountForEachReportType().get(i);
            if (i == statisticsListsHolder.getReportTypeStringList().size() - 1) {
                LineSign = LineSign + 1;
                row = sheet.createRow(LineSign);
                row.setRowStyle(alignCenterStyle);
                eachCell = row.createCell(0);
                eachCell.setCellValue("Total");
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(1);
//                eachCell.setCellValue(totalDeleteCount);
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(2);
                eachCell.setCellValue(totalReportCount);
                eachCell.setCellStyle(alignCenterStyle);
            }
        }


//        Integer totalDeleteCountForCategory = 0;
        LineSign = LineSign + 2;
        row = sheet.createRow(LineSign);
        cell = row.createCell(0);
        row.setHeight((short) 600);
        cell.setCellValue("Content Category");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(1);
        cell.setCellValue("Deleted count");
        cell.setCellStyle(alignCenterStyle);
        for (int i = 0; i < statisticsListsHolder.getCategoryNameStringList().size(); i++) {
            LineSign = LineSign + 1;
            row = sheet.createRow(LineSign);
            row.setRowStyle(alignCenterStyle);
            HSSFCell eachCell = row.createCell(0);
            eachCell.setCellValue(statisticsListsHolder.getCategoryNameStringList().get(i));
            eachCell = row.createCell(1);
            eachCell.setCellValue(statisticsListsHolder.getDeleteCommentCountForEachCategory().get(i));
            eachCell.setCellStyle(alignCenterStyle);
//            totalDeleteCountForCategory = totalDeleteCountForCategory + statisticsListsHolder.getDeleteCommentCountForEachCategory().get(i);
            if (i == statisticsListsHolder.getCategoryNameStringList().size() - 1) {
                LineSign = LineSign + 1;
                row = sheet.createRow(LineSign);
                row.setRowStyle(alignCenterStyle);
                eachCell = row.createCell(0);
                eachCell.setCellValue("Total");
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(1);
//                eachCell.setCellValue(totalDeleteCountForCategory);
                eachCell.setCellStyle(alignCenterStyle);
            }
        }

        Integer totalDeleteCountForContents = 0;
        LineSign = LineSign + 2;
        row = sheet.createRow(LineSign);
        cell = row.createCell(0);
        row.setHeight((short) 600);
        cell.setCellValue("Name of Content");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(1);
        cell.setCellValue("Deleted count");
        cell.setCellStyle(alignCenterStyle);
        for (int i = 0; i < statisticsListsHolder.getContentsTitleStringList().size(); i++) {
            LineSign = LineSign + 1;
            row = sheet.createRow(LineSign);
            row.setRowStyle(alignCenterStyle);
            HSSFCell eachCell = row.createCell(0);
            eachCell.setCellValue(statisticsListsHolder.getContentsTitleStringList().get(i));
            eachCell = row.createCell(1);
            eachCell.setCellValue(statisticsListsHolder.getDeleteCommentCountForEachContents().get(i));
            eachCell.setCellStyle(alignCenterStyle);
            totalDeleteCountForContents = totalDeleteCountForContents + statisticsListsHolder.getDeleteCommentCountForEachContents().get(i);
            if (i == statisticsListsHolder.getContentsTitleStringList().size() - 1) {
                LineSign = LineSign + 1;
                row = sheet.createRow(LineSign);
                row.setRowStyle(alignCenterStyle);
                eachCell = row.createCell(0);
                eachCell.setCellValue("Total");
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(1);
                eachCell.setCellValue(totalDeleteCountForContents);
                eachCell.setCellStyle(alignCenterStyle);

                wb.getSheet(statisticsListsHolder.getSheetNameList().get(0)).getRow(statisticsListsHolder.getContentsStatisticsList().size()+3).getCell(4).setCellValue(totalDeleteCountForContents);
                wb.getSheet(statisticsListsHolder.getSheetNameList().get(1)).getRow(statisticsListsHolder.getReportCountForEachReportType().size()+2).getCell(1).setCellValue(totalDeleteCountForContents);
                wb.getSheet(statisticsListsHolder.getSheetNameList().get(1)).getRow(statisticsListsHolder.getReportCountForEachReportType().size()+3+statisticsListsHolder.getCategoryNameStringList().size()+2).getCell(1).setCellValue(totalDeleteCountForContents);

            }
        }

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        return wb;
    }

    /*
    create sheet1
     */
    static HSSFWorkbook createContentsStatisticsSheet(HSSFWorkbook wb, StatisticsListsHolder statisticsListsHolder) {
        List<ContentsStatistics> list = statisticsListsHolder.getContentsStatisticsList();
        String title = statisticsListsHolder.getTableTitlesList().get(0);
        String sheetName = statisticsListsHolder.getSheetNameList().get(0);
        HSSFSheet sheet = wb.createSheet(sheetName);
//        HSSFSheet sheet = wb.createSheet("statistics");
        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 60 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 15 * 256);
        sheet.setColumnWidth(5, 6 * 256);
        sheet.setColumnWidth(6, 6 * 256);
        sheet.setColumnWidth(7, 6 * 256);
        sheet.setColumnWidth(8, 6 * 256);
        sheet.setColumnWidth(9, 6 * 256);
        sheet.setColumnWidth(10, 6 * 256);
        sheet.setColumnWidth(11, 6 * 256);
        sheet.setColumnWidth(12, 25 * 256);
        sheet.setColumnWidth(13, 25 * 256);
        //row0
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 600);
        HSSFCell titleCell = row.createCell(0);
        titleCell.setCellValue(title);
        HSSFCellStyle alignCenterStyle = wb.createCellStyle();
        alignCenterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        titleCell.setCellStyle(alignCenterStyle);
        //row1
        row = sheet.createRow(1);
        alignCenterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        row.setHeight((short) 300);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("Monitoring Date");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(1);
        cell.setCellValue("Name of Content");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(2);
        cell.setCellValue("Contents Id");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(3);
        cell.setCellValue("Published time");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(4);
        cell.setCellValue("Deleted Comments Count");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(5);
        cell.setCellValue("Report type");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(12);
        cell.setCellValue("In-house Report Count");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(13);
        cell.setCellValue("Content Category");
        cell.setCellStyle(alignCenterStyle);


        //row2
        row = sheet.createRow(2);
        row.setHeight((short) 300);

        cell = row.createCell(0);
        cell.setCellValue("Monitoring Date");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(1);
        cell.setCellValue("Name of Content");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(2);
        cell.setCellValue("Contents Id");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(3);
        cell.setCellValue("Published time");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(4);
        cell.setCellValue("Deleted Comments Count");
        cell.setCellStyle(alignCenterStyle);

        cell = row.createCell(5);
        cell.setCellValue("(a)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(6);
        cell.setCellValue("(b)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(7);
        cell.setCellValue("(c)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(8);
        cell.setCellValue("(d)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(9);
        cell.setCellValue("(e)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(10);
        cell.setCellValue("(f)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(11);
        cell.setCellValue("(g)");
        cell.setCellStyle(alignCenterStyle);

        cell = row.createCell(12);
        cell.setCellValue("In-house Report Count");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(13);
        cell.setCellValue("Content Category");
        cell.setCellStyle(alignCenterStyle);

        Integer topRows = 3;
        List<Integer> cellMergeCols = new ArrayList<Integer>();
        String mergeSign = null;
//        Integer totalDeleteCount = 0;
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + topRows);
            row.setRowStyle(alignCenterStyle);
            HSSFCell eachCell = row.createCell(0);
            ContentsStatistics cS = list.get(i);
            if (StringUtils.isEmpty(mergeSign) || !mergeSign.equals(cS.getHandleDateString())) {
                mergeSign = cS.getHandleDateString();
                cellMergeCols.add(i + topRows);
            }
            eachCell.setCellValue(cS.getHandleDateString());
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(1);
            if (cS.getContentName() != null) {
                eachCell.setCellValue(cS.getContentName());
            } else {
                eachCell.setCellValue("-");
            }
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(2);
            if (cS.getContentsId() != null) {
                eachCell.setCellValue(cS.getContentsId());
            } else {
                eachCell.setCellValue("-");
            }
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(3);
            if (cS.getPublishDate() != null) {
                eachCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cS.getPublishDate()));
            } else {
                eachCell.setCellValue("-");
            }
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(4);
            if (cS.getDeleteCommentNumber() != null) {
                eachCell.setCellValue(cS.getDeleteCommentNumber());
            } else {
                eachCell.setCellValue("-");
            }
            eachCell.setCellStyle(alignCenterStyle);

            eachCell = row.createCell(5);
            eachCell.setCellValue(cS.getReportTypeDelNumArray()[0]);
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(6);
            eachCell.setCellValue(cS.getReportTypeDelNumArray()[1]);
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(7);
            eachCell.setCellValue(cS.getReportTypeDelNumArray()[2]);
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(8);
            eachCell.setCellValue(cS.getReportTypeDelNumArray()[3]);
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(9);
            eachCell.setCellValue(cS.getReportTypeDelNumArray()[4]);
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(10);
            eachCell.setCellValue(cS.getReportTypeDelNumArray()[5]);
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(11);
            eachCell.setCellValue(cS.getReportTypeDelNumArray()[6]);
            eachCell.setCellStyle(alignCenterStyle);


            eachCell = row.createCell(12);
            if (cS.getInHouseAccounts() != null) {
                eachCell.setCellValue(cS.getInHouseAccounts());
            } else {
                eachCell.setCellValue("-");
            }
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(13);
            if (cS.getCategory() != null) {
                eachCell.setCellValue(cS.getCategory());
            } else {
                eachCell.setCellValue("-");
            }
//            eachCell.setCellStyle(alignCenterStyle);
//            if (cS.getDeleteCommentNumber() != null) {
//                totalDeleteCount = totalDeleteCount + cS.getDeleteCommentNumber();
//            }
            if (i == list.size() - 1) {
                cellMergeCols.add(list.size() + topRows);
                row = sheet.createRow(i + topRows + 1);
                row.setRowStyle(alignCenterStyle);
                eachCell = row.createCell(0);
                eachCell.setCellValue("total delete comments");
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(4);
//                eachCell.setCellValue(totalDeleteCount);
                eachCell.setCellStyle(alignCenterStyle);
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 11));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 12, 12));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 13, 13));
        for (int i = 1; i < cellMergeCols.size(); i++) {
            int from = cellMergeCols.get(i - 1);
            int to = cellMergeCols.get(i) - 1;
            if (to < from) {
                to = from;
            }
            sheet.addMergedRegion(new CellRangeAddress(from, to, 0, 0));
        }
//        try {
//            File fileDir = new File(filePath + "/statistics/");
//            if (!fileDir.exists()) {
//                fileDir.mkdirs();
//            }
//            excelFile = new File(filePath + "/statistics/" + title + ".xls");
//            FileOutputStream fout = new FileOutputStream(excelFile);
//            wb.write(fout);
//            fout.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.err.println(excelFile.length());
        return wb;
    }

    static String getHourString (String hour)
    {
        Integer hourInt = Integer.valueOf(hour);
        String hourString = "";
        switch(hourInt) {
            case 0:
                hourString = "00:00~01:00";
            break;
            case 1:
                hourString = "01:00~02:00";
            break;
            case 2:
                hourString = "02:00~03:00";
            break;
            case 3:
                hourString = "03:00~04:00";
            break;
            case 4:
                hourString = "04:00~05:00";
            break;
            case 5:
                hourString = "05:00~06:00";
            break;
            case 6:
                hourString = "06:00~07:00";
            break;
            case 7:
                hourString = "07:00~08:00";
            break;
            case 8:
                hourString = "08:00~09:00";
            break;
            case 9:
                hourString = "09:00~10:00";
            break;
            case 10:
                hourString = "10:00~11:00";
            break;
            case 11:
                hourString = "11:00~12:00";
            break;
            case 12:
                hourString = "12:00~13:00";
            break;
            case 13:
                hourString = "13:00~14:00";
            break;
            case 14:
                hourString = "14:00~15:00";
            break;
            case 15:
                hourString = "15:00~16:00";
            break;
            case 16:
                hourString = "16:00~17:00";
            break;
            case 17:
                hourString = "17:00~18:00";
            break;
            case 18:
                hourString = "18:00~19:00";
            break;
            case 19:
                hourString = "19:00~20:00";
            break;
            case 20:
                hourString = "20:00~21:00";
            break;
            case 21:
                hourString = "21:00~22:00";
            break;
            case 22:
                hourString = "22:00~23:00";
            break;
            case 23:
                hourString = "23:00~24:00";
            break;
        }
        return hourString;
    }
    static HSSFWorkbook createTimeLineStatusticsSheet(HSSFWorkbook wb, StatisticsListsHolder statisticsListsHolder) {
        List<TimeLineStatistics> perDayList = statisticsListsHolder.getPerDayTimeLineStatisticsList();
        List<TimeLineStatistics> wholePeriodList = statisticsListsHolder.getWholePeriodTimeLineStatisticsList();
        String title = statisticsListsHolder.getTableTitlesList().get(0);
        String sheetName = statisticsListsHolder.getSheetNameList().get(2);
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 15 * 256);
        sheet.setColumnWidth(2, 15 * 256);
        sheet.setColumnWidth(3, 15 * 256);
        sheet.setColumnWidth(4, 6 * 256);
        sheet.setColumnWidth(5, 6 * 256);
        sheet.setColumnWidth(6, 6 * 256);
        sheet.setColumnWidth(7, 6 * 256);
        sheet.setColumnWidth(8, 6 * 256);
        sheet.setColumnWidth(9, 6 * 256);
        sheet.setColumnWidth(10, 6 * 256);

        sheet.setColumnWidth(11, 15 * 256);

        sheet.setColumnWidth(12, 15 * 256);
        sheet.setColumnWidth(13, 15 * 256);
        sheet.setColumnWidth(14, 15 * 256);
        sheet.setColumnWidth(15, 6 * 256);
        sheet.setColumnWidth(16, 6 * 256);
        sheet.setColumnWidth(17, 6 * 256);
        sheet.setColumnWidth(18, 6 * 256);
        sheet.setColumnWidth(19, 6 * 256);
        sheet.setColumnWidth(20, 6 * 256);
        sheet.setColumnWidth(21, 6 * 256);
        sheet.setColumnWidth(22, 15 * 256);

        HSSFRow row = sheet.createRow(0);
        row.setHeight((short) 600);
        HSSFCell titleCell = row.createCell(0);
        titleCell.setCellValue(title);
        HSSFCellStyle alignCenterStyle = wb.createCellStyle();
        alignCenterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        titleCell.setCellStyle(alignCenterStyle);

        row = sheet.createRow(1);
        alignCenterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        row.setHeight((short) 300);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("Input Time Line of Deleted Comments");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(1);
        cell.setCellValue("Input Time Line of Deleted Comments");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(2);
        cell.setCellValue("Input Time Line of Deleted Comments");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(3);
        cell.setCellValue("Input Time Line of Deleted Comments");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(4);
        cell.setCellValue("Report type");
        cell.setCellStyle(alignCenterStyle);

        cell = row.createCell(12);
        cell.setCellValue("Input Time Line of Deleted Comments");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(13);
        cell.setCellValue("Input Time Line of Deleted Comments");
        cell.setCellStyle(alignCenterStyle);

        cell = row.createCell(15);
        cell.setCellValue("Report type");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(22);
        cell.setCellValue("Total");
        cell.setCellStyle(alignCenterStyle);

        row = sheet.createRow(2);
        row.setHeight((short) 300);

        cell = row.createCell(0);
        cell.setCellValue("Input Time Line of Deleted Comments");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(1);
        cell.setCellValue("Input Time Line of Deleted Comments");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(2);
        cell.setCellValue("Input Time Line of Deleted Comments");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(3);
        cell.setCellValue("Input Time Line of Deleted Comments");
        cell.setCellStyle(alignCenterStyle);

        cell = row.createCell(4);
        cell.setCellValue("(a)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(5);
        cell.setCellValue("(b)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(6);
        cell.setCellValue("(c)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(7);
        cell.setCellValue("(d)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(8);
        cell.setCellValue("(e)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(9);
        cell.setCellValue("(f)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(10);
        cell.setCellValue("(g)");
        cell.setCellStyle(alignCenterStyle);

        cell = row.createCell(12);
        cell.setCellValue("Input Time Line of Deleted Comments");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(13);
        cell.setCellValue("Input Time Line of Deleted Comments");
        cell.setCellStyle(alignCenterStyle);

        cell = row.createCell(15);
        cell.setCellValue("(a)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(16);
        cell.setCellValue("(b)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(17);
        cell.setCellValue("(c)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(18);
        cell.setCellValue("(d)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(19);
        cell.setCellValue("(e)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(20);
        cell.setCellValue("(f)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(21);
        cell.setCellValue("(g)");
        cell.setCellStyle(alignCenterStyle);
        cell = row.createCell(22);
        cell.setCellValue("Total");
        cell.setCellStyle(alignCenterStyle);

        Integer topRows = 3;
        List<Integer> cellMergeCols = new ArrayList<Integer>();
        String mergeSign = null;
        Integer totalDeleteCount = 0;
        for (int i = 0; i < perDayList.size(); i++) {
            row = sheet.createRow(i + topRows);
            row.setRowStyle(alignCenterStyle);
            HSSFCell eachCell = row.createCell(0);
            TimeLineStatistics perts = perDayList.get(i);
            if (StringUtils.isEmpty(mergeSign) || !mergeSign.equals(perts.getInputDate())) {
                mergeSign = perts.getInputDate();
                cellMergeCols.add(i + topRows);
            }
            eachCell.setCellValue(perts.getInputDate());
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(1);
            eachCell.setCellValue(getHourString(perts.getInputHour()));
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(2);
            eachCell.setCellValue(perts.getDeleteCommentCount());
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(4);
            eachCell.setCellValue(perts.getReportTypeDelNumArray()[0]);
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(5);
            eachCell.setCellValue(perts.getReportTypeDelNumArray()[1]);
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(6);
            eachCell.setCellValue(perts.getReportTypeDelNumArray()[2]);
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(7);
            eachCell.setCellValue(perts.getReportTypeDelNumArray()[3]);
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(8);
            eachCell.setCellValue(perts.getReportTypeDelNumArray()[4]);
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(9);
            eachCell.setCellValue(perts.getReportTypeDelNumArray()[5]);
            eachCell.setCellStyle(alignCenterStyle);
            eachCell = row.createCell(10);
            eachCell.setCellValue(perts.getReportTypeDelNumArray()[6]);
            eachCell.setCellStyle(alignCenterStyle);

            if (i == perDayList.size() - 1) {
                cellMergeCols.add(perDayList.size() + topRows);
            }

            if (i < wholePeriodList.size()) {
                TimeLineStatistics wpts = wholePeriodList.get(i);

                eachCell = row.createCell(13);
                eachCell.setCellValue(getHourString(wpts.getInputHour()));
                eachCell.setCellStyle(alignCenterStyle);

                eachCell = row.createCell(15);
                eachCell.setCellValue(wpts.getReportTypeDelNumArray()[0]);
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(16);
                eachCell.setCellValue(wpts.getReportTypeDelNumArray()[1]);
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(17);
                eachCell.setCellValue(wpts.getReportTypeDelNumArray()[2]);
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(18);
                eachCell.setCellValue(wpts.getReportTypeDelNumArray()[3]);
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(19);
                eachCell.setCellValue(wpts.getReportTypeDelNumArray()[4]);
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(20);
                eachCell.setCellValue(wpts.getReportTypeDelNumArray()[5]);
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(21);
                eachCell.setCellValue(wpts.getReportTypeDelNumArray()[6]);
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(22);
                eachCell.setCellValue(wpts.getDeleteCommentCount());
                eachCell.setCellStyle(alignCenterStyle);

                if (wpts.getDeleteCommentCount() != null) {
                    totalDeleteCount = totalDeleteCount + wpts.getDeleteCommentCount();
                }
            } else if (i == wholePeriodList.size()) {
                eachCell = row.createCell(13);
                eachCell.setCellValue("total delete comments");
                eachCell.setCellStyle(alignCenterStyle);
                eachCell = row.createCell(22);
                eachCell.setCellValue(totalDeleteCount);
                eachCell.setCellStyle(alignCenterStyle);
            }
        }
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 3));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 10));

        sheet.addMergedRegion(new CellRangeAddress(1, 2, 12, 13));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 15, 21));
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 22, 22));
        for (int j = 1; j < cellMergeCols.size(); j++) {
            int from = cellMergeCols.get(j - 1);
            int to = cellMergeCols.get(j) - 1;
            if (to < from) {
                to = from;
            }
            sheet.addMergedRegion(new CellRangeAddress(from, to, 0, 0));
        }

        return wb;
    }

    void clearStatisticsExcelFiles(String filePath) throws Exception {
        File fileDir = new File(filePath + "/statistics/");
        if (fileDir.exists()) {
            FileUtils.delete(fileDir);
        }
    }
}
