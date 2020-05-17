package cn.iocoder.springboot.ex01.fastdfs.entity;

import lombok.Data;

/**
 * @author yixiaoying@geostar.com
 * @date 2020/3/6 20:05
 */
@Data
public class FileEntity {
    private byte[] bytes;
    private long length;
    private String fileName;
}
