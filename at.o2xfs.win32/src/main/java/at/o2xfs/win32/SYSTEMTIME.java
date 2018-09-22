/*
 * Copyright (c) 2017, Andreas Fagschlunger. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package at.o2xfs.win32;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

/**
 * @see <a href=
 *      "http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx">http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx</a>
 *
 * @author Andreas Fagschlunger
 */
public class SYSTEMTIME extends Struct implements ValueType<LocalDateTime> {

	public static final int SUNDAY = 0;

	private WORD year = new WORD();
	private WORD month = new WORD();
	private WORD dayOfWeek = new WORD();
	private WORD day = new WORD();
	private WORD hour = new WORD();
	private WORD minute = new WORD();
	private WORD second = new WORD();
	private WORD milliseconds = new WORD();

	public SYSTEMTIME() {
		add(year);
		add(month);
		add(dayOfWeek);
		add(day);
		add(hour);
		add(minute);
		add(second);
		add(milliseconds);
	}

	@Override
	public void set(LocalDateTime value) {
		year.set(value.getYear());
		month.set(value.getMonthValue());
		dayOfWeek.set(DayOfWeek.SUNDAY == value.getDayOfWeek() ? SUNDAY : value.getDayOfWeek().getValue());
		day.set(value.getDayOfMonth());
		hour.set(value.getHour());
		minute.set(value.getMinute());
		second.set(value.getSecond());
		milliseconds.set(value.get(ChronoField.MILLI_OF_SECOND));
	}

	@Override
	public LocalDateTime get() {
		return LocalDateTime
				.of(getYear(), getMonth(), getDay(), getHour(), getMinute(), getSecond(), getMilliseconds() * 1000000);
	}

	public void set(SYSTEMTIME value) {
		put(value.getBytes());
	}

	public int getYear() {
		return year.intValue();
	}

	public void setYear(int year) {
		this.year.set(year);
	}

	public int getMonth() {
		return month.intValue();
	}

	public void setMonth(int month) {
		this.month.set(month);
	}

	public int getDayOfWeek() {
		return dayOfWeek.intValue();
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek.set(dayOfWeek);
	}

	public int getDay() {
		return day.intValue();
	}

	public void setDay(int day) {
		this.day.set(day);
	}

	public int getHour() {
		return hour.intValue();
	}

	public void setHour(int hour) {
		this.hour.set(hour);
	}

	public int getMinute() {
		return minute.intValue();
	}

	public void setMinute(int minute) {
		this.minute.set(minute);
	}

	public int getSecond() {
		return second.intValue();
	}

	public void setSecond(int second) {
		this.second.set(second);
	}

	public int getMilliseconds() {
		return milliseconds.intValue();
	}

	public void setMilliseconds(int milliseconds) {
		this.milliseconds.set(milliseconds);
	}
}