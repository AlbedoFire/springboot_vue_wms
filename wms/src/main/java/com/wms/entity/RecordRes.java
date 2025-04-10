package com.wms.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class RecordRes extends  Record{

    private String username;
    private String adminname;
    private String goodsname;
    private String storagename;
    private String goodstypename;
}
