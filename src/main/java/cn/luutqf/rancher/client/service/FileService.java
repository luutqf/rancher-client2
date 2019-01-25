package cn.luutqf.rancher.client.service;

/**
 * @Author: ZhenYang
 * @date: 2019/1/24
 * @description:
 */
public interface FileService {
    /**
     *
     * @param path
     * @return
     */
    Boolean create(String path);

    /**
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    Boolean move(String oldPath,String newPath);

    /**
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    Boolean copy(String oldPath,String newPath);

    /**
     *
     * @param path
     * @return
     */
    Boolean delete(String path);

    /**
     *
     * @param path
     * @return
     */
    Boolean chmod(String path);
}
