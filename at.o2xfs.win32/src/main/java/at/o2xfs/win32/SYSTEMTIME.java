/*
 * Copyright (c) 2014, Andreas Fagschlunger. All rights reserved.
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

/**
 * @see <a href=
 *      "http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx">http://msdn.microsoft.com/en-us/library/aa383751%28v=vs.85%29.aspx</a>
 *
 * @author Andreas Fagschlunger
 */
public class SYSTEMTIME extends Struct {

	private final WORD year = new WORD();
	private final WORD month = new WORD();
	private final WORD dayOfWeek = new WORD();
	private final WORD day = new WORD();
	private final WORD hour = new WORD();
	private final WORD minute = new WORD();
	private final WORD second = new WORD();
	private final WORD milliseconds = new WORD();

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

	public WORD getYear() {
		return year;
	}

	public WORD getMonth() {
		return month;
	}

	public WORD getDayOfWeek() {
		return dayOfWeek;
	}

	public WORD getDay() {
		return day;
	}

	public WORD getHour() {
		return hour;
	}

	public WORD getMinute() {
		return minute;
	}

	public WORD getSecond() {
		return second;
	}

	public WORD getMilliseconds() {
		return milliseconds;
	}
}