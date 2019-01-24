package cn.luutqf.rancher.client.service;

/**
 * @Author: ZhenYang
 * @date: 2019/1/24
 * @description:
 */
public interface FileService {
    Boolean create(String path);

    Boolean move(String oldPath,String newPath);

    Boolean copy(String oldPath,String newPath);

    Boolean delete(String path);

    Boolean chmod(String path);
}
