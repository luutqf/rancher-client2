package cn.luutqf.rancher.client.service;

import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;
import io.rancher.base.TypeCollection;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import io.rancher.type.ContainerLogs;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public interface  ChapterService<T extends Chapter> {

    default String add(T t){ return "nq tp tq"; }

    default Object delete(String id){ return "nq tp tq"; }

    default Object start(String id){ return "nq tp tq"; }

    default Object stop(String id){ return "nq tp tq"; }

    default String findUrl( String id){ return "nq tp tq"; }

    default Container find( String id){ return null; }

    default String getToken(String id){ return null; }

    default  String getContainerName(T t) {
        try {
            Class<?> aClass = null;
            if(t.getClass() == Chapter.class){
                aClass = t.getClass();
            }else{
                aClass = t.getClass().getSuperclass();
            }
            Field usernameF = aClass.getDeclaredField("username");
            Field imageF = aClass.getDeclaredField("image");
            Field chapterNameF = aClass.getDeclaredField("chapterName");
            imageF.setAccessible(true);
            usernameF.setAccessible(true);
            chapterNameF.setAccessible(true);
            return usernameF.get(t).toString() + "-"+chapterNameF.get(t).toString()+"-" + imageF.get(t).toString().substring(imageF.get(t).toString().indexOf("/") + 1);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RancherException(e.getMessage(),RancherException.CHAPTER_ERROR);
        }
    }
    default String getId(Container container, T t, String project, ProjectApi api) throws IOException {
        container.setImageUuid(t.getContainerType() + t.getImage());
        container.setName(getContainerName(t));
        Response<Container> execute = api.createContainer(project, container).execute();
        // TODO 把422换成message
        if(execute.code() == 422){
            return getIdUnique(t,project,api);
        }
        container = execute.body();
        return container != null ? container.getId() : null;
    }
    default String getIdUnique( T t, String project, ProjectApi api) throws IOException {
        TypeCollection<Container> body = api.listContainers(project).execute().body();
        if(null == body){
            return null;
        }
        for (Container c:body.getData()){
            boolean name = c.getName().equals(getContainerName(t));
            if(name){
                return c.getId();
            }
        }
        return null;
    }
    default Boolean deleteChapter(Chapter chapter){
        return true;
    }
}
