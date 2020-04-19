package cn.iocoder.springcloud.labx08.gatewaydemo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class Blog{
    private String title;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
}