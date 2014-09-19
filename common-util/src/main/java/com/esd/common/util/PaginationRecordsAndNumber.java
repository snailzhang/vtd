package com.esd.common.util;

import java.util.Collection;

public class PaginationRecordsAndNumber<T, N extends Number> {
	private Collection<T> records;
	private N number;

	public PaginationRecordsAndNumber() {
	}

	public PaginationRecordsAndNumber(Collection<T> records, N number) {
		super();
		this.records = records;
		this.number = number;
	}

	/**
	 * @return the records
	 */
	public Collection<T> getRecords() {
		return records;
	}

	/**
	 * @param records
	 *            the records to set
	 */
	public void setRecords(Collection<T> records) {
		this.records = records;
	}

	/**
	 * @return the number
	 */
	public N getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(N number) {
		this.number = number;
	}
}
