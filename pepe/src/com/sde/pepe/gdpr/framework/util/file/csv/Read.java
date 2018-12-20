package com.sde.pepe.gdpr.framework.util.file.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.sde.pepe.gdpr.application.rs.CustomReadHeader;
import com.sde.pepe.gdpr.framework.entity.impl.FileEntity;
import com.sde.pepe.gdpr.framework.transform.Transformer;

public class Read<T>{

	public List<T> read(FileEntity fileEntity, String[] headerMapping, Transformer<CSVRecord,T> transformer) {
		File f=fileEntity.getF();
		List<T> records = new ArrayList<T>();
		try {
			T record=null;
			
			Reader reader = new FileReader(f);
			CSVFormat format = CSVFormat.EXCEL.withHeader(headerMapping).withSkipHeaderRecord();
			CSVParser parser = format.parse(reader);
			List<CSVRecord> csvRecords = parser.getRecords();
			
			for (CSVRecord csvRecord : csvRecords) {
				record=transformer.getEntity(csvRecord);
				records.add(record);
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
			
		return records;		
	}//read
	public List<T> read(FileEntity fileEntity, Transformer<CSVRecord,T> transformer, CustomReadHeader readHeader) {
		File f=fileEntity.getF();
		List<T> records = new ArrayList<T>();
		String[] headerMapping=readHeader.getHeaderMapping();
		try {
			T record=null;
			
			Reader reader = new FileReader(f);
			CSVFormat format = CSVFormat.EXCEL.withHeader(headerMapping).withSkipHeaderRecord();
			CSVParser parser = format.parse(reader);
			List<CSVRecord> csvRecords = parser.getRecords();
			
			for (CSVRecord csvRecord : csvRecords) {
				record=transformer.getEntity(csvRecord,readHeader);
				records.add(record);
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
			
		return records;		
	}//read	

	public List<T> readAll(FileEntity fileEntity, Transformer<CSVRecord,T> transformer, CustomReadHeader readHeader) {
		File f=fileEntity.getF();
		List<T> allRecords = new ArrayList<T>();
		String[] headerMapping=readHeader.getHeaderMapping();
		try {
			List<T> records=null;
			
			Reader reader = new FileReader(f);
			CSVFormat format = CSVFormat.EXCEL.withHeader(headerMapping).withSkipHeaderRecord();
			CSVParser parser = format.parse(reader);
			List<CSVRecord> csvRecords = parser.getRecords();
			
			for (CSVRecord csvRecord : csvRecords) {
				records=transformer.getEntities(csvRecord,readHeader);
				allRecords.addAll(records);
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
			
		return allRecords;		
	}//readAll	
}
