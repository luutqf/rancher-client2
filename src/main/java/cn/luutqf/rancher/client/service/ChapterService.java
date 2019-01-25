package cn.luutqf.rancher.client.service;

import cn.luutqf.rancher.client.model.Chapter;
import io.rancher.service.ProjectApi;
import io.rancher.type.Container;
import retrofit2.Response;

import java.util.Optional;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public interface ChapterService<T extends Chapter> extends ContainerService<T> {


    /**
     * 创建一个章节容器
     * @param t
     * @return
     */
    Optional<String> createChapter(T t);

    /**
     * 查询一个章节容器URL
     * @param id
     * @return
     */
    Optional<String> findChapterUrl(String id);


    /**
     * 删除一个章节容器
     * @param chapter
     * @return
     */
    Boolean deleteChapter(T chapter);


    /**
     * 获取一个章节容器的名字
     * @param t
     * @return
     */
    Optional<String> getChapterContainerName(T t);


    /**
     * 获取章节容器ID
     * @param execute
     * @param t
     * @param project
     * @param api
     * @return
     */
    Optional<String> getChapterId(Response<Container> execute, T t, String project, ProjectApi api);


    /**
     * 默认方法，得到固定格式的章节容器文件夹路径
     * @param t
     * @return
     */
    default String getFilePath(T t){

        return t.getUsername() + "/" + t.getSubjectName() + "/" + t.getChapterName();
    }

//    default String getContainerName(T t) {
//        Logger log = Logger.getLogger(this.getClass().getName());
//        try {
//            Class<?> aClass;
//            if (t.getClass() == Chapter.class) aClass = t.getClass();
//            else aClass = t.getClass().getSuperclass();
//            Field usernameF = aClass.getDeclaredField("username");
//            Field imageF = aClass.getDeclaredField("image");
//            Field chapterNameF = aClass.getDeclaredField("chapterName");
//            imageF.setAccessible(true);
//            usernameF.setAccessible(true);
//            chapterNameF.setAccessible(true);
//            Object username = usernameF.get(t);
//            Object chapterName = chapterNameF.get(t);
//            Object image = imageF.get(t);
//            if (!(Optional.ofNullable(image).isPresent() || Optional.ofNullable(username).isPresent() || Optional.ofNullable(chapterName).isPresent())) {
//                throw new RancherException("null???", RancherException.CHAPTER_ERROR);
//            }
//            if (username != null) {
//                if (image != null) {
//                    return username.toString() + "-" + chapterName.toString() + "-" + image.toString().substring(image.toString().indexOf("/") + 1);
//                }
//            }
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            log.warning("Class Field の problem :" + toString());
//            throw new RancherException(e.getMessage(), RancherException.CHAPTER_ERROR);
//        }
//        throw new RancherException("", RancherException.CHAPTER_ERROR);
//    }

//    default Optional<String> createContainerForId(Container container, T t, String project, ProjectApi api) {
//        Response<Container> execute = createContainer(container, t, project, api);
//        if (Objects.requireNonNull(execute).message().startsWith("Unprocessable")) {
//            return getIdUnique(t, project, api);
//        }
//        if (Optional.ofNullable(execute.body()).isPresent())
//            return Optional.ofNullable(execute.body().getId());
//        return Optional.empty();
//    }

//    default Response<Container> createContainer(Container container, T t, String project, ProjectApi api) {
//        container.setImageUuid(t.getContainerType() + t.getImage());
//        Optional<String> chapterContainerName = getChapterContainerName(t);
//        if (chapterContainerName.isPresent())
//            container.setName(chapterContainerName.get());
//        else
//            throw new RancherException(RancherException.CHAPTER_FIELD_EMPTY);
//        try {
//            return api.createContainer(project, container).execute();
//        } catch (IOException e) {
//            throw new RancherException(e.getMessage(), RancherException.CREATE_CHAPTER_ERROR);
//        }
//    }

//    default Optional<String> getIdUnique(T t, String project, ProjectApi api) {
//        TypeCollection<Container> body;
//        try {
//            body = api.listContainers(project).execute().body();
//        } catch (IOException e) {
//            throw new RancherException(e.getMessage(), RancherException.LIST_CONTAINER_ERROR);
//        }
//        if (!Optional.ofNullable(body).isPresent()) {
//            return Optional.empty();
//        }
//        for (Container c : body.getData()) {
//            Optional<String> chapterContainerName = getChapterContainerName(t);
//            if(!chapterContainerName.isPresent()){
//                throw new RancherException(RancherException.CHAPTER_FIELD_EMPTY);
//            }
//            boolean name = c.getName().equals(chapterContainerName.get());
//            if (name) {
//                return Optional.ofNullable(c.getId());
//            }
//        }
//        return Optional.empty();
//    }


}
