package committee.nova.mods.cmdkey.utils;

/**
 * @Project: CmdKey
 * @Author: cnlimiter
 * @CreateTime: 2025/3/16 17:53
 * @Description:
 */
public interface IFunction<T, R> {
    R apply(T t);
}
