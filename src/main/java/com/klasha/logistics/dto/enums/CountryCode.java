package com.klasha.logistics.dto.enums;

import java.util.Arrays;
import java.util.List;

public enum CountryCode {

    USA,CAN,MEX,GBR,FRA,DEU,ITA,ESP,AUS,NZL,JPN,KOR,CHN,IND,BRA,ARG,CHL,COL,PER,ZAF,NGA,KEN,TZA,UGA,EGY,SAU,ARE,QAT,KWT,
    BHR,OMN,JOR,LBN,ISR,TUR,RUS,KAZ,UKR,BLR,POL,CZE,HUN,SVK,ROU,BGR,GRC,SRB,HRV,SVN,LTU,LVA,EST,FIN,SWE,NOR,DNK,ISL,CHE,
    AUT,BEL,NLD,IRL,PRT,GHA;

    public static List<CountryCode> getActorTypes(){
        return Arrays.asList(CountryCode.values());
    }

    }
