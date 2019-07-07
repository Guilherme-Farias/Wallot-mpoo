package com.ufrpe.bsi.mpoo.wallotapp.orcamento.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;

public class LinearRegression {
    private ArrayList<BigDecimal> Xdata;
    private ArrayList<BigDecimal> YData;
    private BigDecimal result1;
    private BigDecimal result2;

    public LinearRegression (ArrayList xData, ArrayList yData) {
        Xdata = xData;
        this.YData = yData;
    }

    public BigDecimal predictValue ( BigDecimal inputValue ) {
        BigDecimal X1 = Xdata.get( 0 ) ;
        BigDecimal Y1 = YData.get( 0 ) ;
        BigDecimal Xmean = getXMean( Xdata ) ;
        BigDecimal Ymean = getYMean(YData ) ;
        BigDecimal lineSlope = getLineSlope( Xmean , Ymean , X1 , Y1 ) ;
        BigDecimal YIntercept = getYIntercept( Xmean , Ymean , lineSlope ) ;
        BigDecimal prediction = ( lineSlope.multiply(inputValue)).add(YIntercept) ;
        return prediction ;
    }

    public BigDecimal getLineSlope (BigDecimal Xmean, BigDecimal Ymean, BigDecimal X1, BigDecimal Y1) {
        BigDecimal num1 = X1.subtract(Xmean);
        BigDecimal num2 = Y1.subtract(Ymean);
        BigDecimal denom = (X1.subtract(Xmean)).multiply((X1.subtract(Xmean)));
        return ((num1.multiply(num2)).divide(denom, BigDecimal.ROUND_UP));
    }


    public BigDecimal getYIntercept (BigDecimal Xmean, BigDecimal Ymean, BigDecimal lineSlope) {
        return Ymean.subtract((lineSlope.multiply(Xmean)));
    }

    public BigDecimal getXMean (ArrayList<BigDecimal> Xdata) {
        result1 = new BigDecimal("0.00") ;
        for (Integer i = 0; i < Xdata.size(); i++) {
            result1 = result1.add(Xdata.get(i));
        }
        return result1;
    }

    public BigDecimal getYMean (ArrayList<BigDecimal> Ydata) {
        result2 = new BigDecimal("0.00") ;
        for (Integer i = 0; i < Ydata.size(); i++) {
            result2 = result2.add(Ydata.get(i));
        }
        return result2;
    }


}
