package com.indulge.freedom.who.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;

public class InputFilterMinMax implements InputFilter {
	private double min, max;

	private int digitsAfterZero;

	private Pattern mPattern;

	private Context context;

	public InputFilterMinMax(Context context, double min, double max,
			int digitsBeforeZero, int digitsAfterZero) {
		this.min = min;
		this.max = max;
		this.context = context;
		this.digitsAfterZero = digitsAfterZero;
		mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1)
				+ "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		Matcher matcher = mPattern.matcher(dest);
		Log.i("H2", "source:" + source.toString() + "--start:" + start
				+ "--end:" + end + "--dest:" + dest.toString() + "--dstart:"
				+ dstart + "--dend:" + dend);
		

		String newVal = dest.toString().substring(0, dstart)
				+ dest.toString().substring(dend, dest.toString().length());
		// Add the new string in
		newVal = newVal.substring(0, dstart) + source.toString()
				+ newVal.substring(dstart, newVal.length());
		
		if (!matcher.matches()) {
			if(newVal.length()>0){
				if (min - (int) min == 0 && max - (int) max == 0) {
					ToastUtil.show(context, "请输入" + (int) min + "~" + (int) max
							+ "之间的金额");
				} else {
					ToastUtil.show(context, "请输入" + min + "~" + max + "之间的金额");
				}
			}
			return "";
		}
		if (min - (int) min == 0 && max - (int) max == 0) {
			if(newVal.equals("0")){
				return "";
			}
		}
		try {
			double input = Double.parseDouble(newVal);
			int iinput = (int) input;
			int imin = (int) min;

			int inputLength = String.valueOf(iinput).length();
			int iminLength = String.valueOf(imin).length();
			if (iminLength == inputLength) {
				if (iinput > imin) {
					if (isInRange(min, max, input))
						return null;
				} else if (iinput < imin) {
					if (!newVal.contains(".")) {
						if (input <= max) {
							return null;
						}
					}
				} else if (iinput == imin) {
					int index = -1;
					if (newVal.contains(".")) {
						index = newVal.indexOf(".");
						if (index == newVal.length() - 1) {
							return null;
						} else if (index == newVal.length() - 2) {
							if (Integer.valueOf(newVal.substring(index + 1,
									index + 2)) >= Integer.valueOf(String
									.valueOf(min).substring(index + 1,
											index + 2))) {
								return null;
							}
						} else {
							if (isInRange(min, max, input))
								return null;
						}
					} else {
						return null;
					}
				}
			} else if (iminLength < inputLength) {
				if (isInRange(min, max, input))
					return null;
			} else {
				return null;
			}
		} catch (NumberFormatException nfe) {
		}
		if (!TextUtils.isEmpty(newVal)) {
			if (min - (int) min == 0 && max - (int) max == 0) {
				ToastUtil.show(context, "请输入" + (int) min + "~" + (int) max
						+ "之间的金额");
			} else {
				ToastUtil.show(context, "请输入" + min + "~" + max + "之间的金额");
			}
		}
		return "";
	}

	private boolean isInRange(double a, double b, double c) {
		return b > a ? c >= a && c <= b : c >= b && c <= a;
	}
}
