package impl;

public class UnknownProtocolException extends Exception
{
    public UnknownProtocolException(int id)
    {
        super("Received packet with unknown id: " + id);
    }

}