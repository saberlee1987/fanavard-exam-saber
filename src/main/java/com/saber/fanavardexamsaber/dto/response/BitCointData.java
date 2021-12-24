package com.saber.fanavardexamsaber.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class BitCointData {
    public String s;
    public List<Long> t;
    public List<Double> c;
    public List<Double> o;
    public List<Double> h;
    public List<Double> l;
    public List<Double> v;
}
