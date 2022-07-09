package impl.connection;

public interface IConnectionListener {
    void onJoin(IConnectionManager var1, IConnection var2);

    void onLeave(IConnectionManager var1, IConnection var2);
}
