package com.dsmh.common.util;

import com.dsmh.common.enumeration.Gender;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author TeamScorpio
 * @since 2022/6/6
 */
public class IdCardUtil {


    public static LocalDate getIdNumBirthday(String idNum) {
        String birthStr = idNum.substring(6, 14);
        LocalDate date = LocalDate.parse(birthStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return date;
    }

    public static Gender getIdNumGender(String idNum) {
        int gender = 0;
        char c;
        if (idNum.length() == 18) {
            //如果身份证号18位，取身份证号倒数第二位
            c = idNum.charAt(idNum.length() - 2);
        } else {
            //如果身份证号15位，取身份证号最后一位
            c = idNum.charAt(idNum.length() - 1);
        }
        gender = Integer.parseInt(String.valueOf(c));
        if (gender % 2 == 1) {
            return Gender.MALE;
        } else {
            return Gender.FEMALE;
        }

    }

    public static void main(String[] args) {
        System.out.println(getIdNumGender("430802199311150338"));
    }

}
