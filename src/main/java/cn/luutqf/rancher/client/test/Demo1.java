package cn.luutqf.rancher.client.test;

import cn.luutqf.rancher.client.model.Chapter;
import cn.luutqf.rancher.client.service.ChapterService;

import java.lang.reflect.Field;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public class Demo1 implements ChapterService {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Child child = new Child();
        System.out.println(child.getName());

        Class<?> aClass = child.getClass();

        Field sex = aClass.getDeclaredField("sex");
        sex.setAccessible(true);
        System.out.println(sex.get(child));

        Field name = aClass.getSuperclass().getDeclaredField("name");
        name.setAccessible(true);
        System.out.println(name.get(child));
    }

    @Override
    public Object add(Chapter chapter) {
        return null;
    }

    @Override
    public Object delete(Chapter chapter) {
        return null;
    }

    @Override
    public Object start(Chapter chapter) {
        return null;
    }

    @Override
    public Object stop(Chapter chapter) {
        return null;
    }
}
