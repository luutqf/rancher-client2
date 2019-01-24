package cn.luutqf.rancher.client.service;

import cn.luutqf.rancher.client.exception.RancherException;
import cn.luutqf.rancher.client.model.Chapter;
import io.rancher.base.TypeCollection;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public interface ChapterService<T extends Chapter> extends ContainerService<T> {

    Optional<String> findUrl(String id);

    Container find(String id);

    Boolean deleteChapter(T chapter);

    default String getContainerName(T t) {
        Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        try {
            Class<?> aClass = null;
            if (t.getClass() == Chapter.class) {
                aClass = t.getClass();
            } else {
                aClass = t.getClass().getSuperclass();
            }
            log.info("Container class type :" +aClass.toString());
            Field usernameF = aClass.getDeclaredField("username");
            Field imageF = aClass.getDeclaredField("image");
            Field chapterNameF = aClass.getDeclaredField("chapterName");
            imageF.setAccessible(true);
            usernameF.setAccessible(true);
            chapterNameF.setAccessible(true);
            return usernameF.get(t).toString() + "-" + chapterNameF.get(t).toString() + "-" + imageF.get(t).toString().substring(imageF.get(t).toString().indexOf("/") + 1);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
        }
    }

    default Optional<String> getId(Container container, T t, String project, ProjectApi api) throws IOException {
        Response<Container> execute = createContainer(container, t, project, api);
        if (execute.message().startsWith("Unprocessable")) {
            return Optional.ofNullable(getIdUnique(t,project,api));
        }
        if(Optional.ofNullable(execute.body()).isPresent())
            return Optional.ofNullable(execute.body().getId());
        return Optional.empty();
    }

    default Response<Container> createContainer(Container container, T t, String project, ProjectApi api) throws IOException {
        container.setImageUuid(t.getContainerType() + t.getImage());
        container.setName(getContainerName(t));
        return api.createContainer(project, container).execute();
    }


    default String getIdUnique(T t, String project, ProjectApi api) throws IOException {
        TypeCollection<Container> body = api.listContainers(project).execute().body();
        if (null == body) {
            return null;
        }
        for (Container c : body.getData()) {
            boolean name = c.getName().equals(getContainerName(t));
            if (name) {
                return c.getId();
            }
        }
        return null;
    }

}
