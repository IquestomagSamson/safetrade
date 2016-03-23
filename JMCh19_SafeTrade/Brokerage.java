import java.lang.reflect.*;
import java.util.*;

/**
 * @author ktirumala940
 */
/**
 * Represents a brokerage.
 */
public class Brokerage implements Login
{
    private Map<String, Trader> traders;
    private Set<Trader> loggedTraders;
    private StockExchange exchange;

    public Brokerage(StockExchange s) {
        traders = new TreeMap<String, Trader>();
        loggedTraders = new TreeSet<Trader>();
        exchange = s;
    }

    
    public int addUser( String name, String password )
    {
        //constructor values
        Trader temp = new Trader(this, name, password);
        if(! (name.length() >= 4 && name.length() <= 10) )
            return -1;
        else if (! (password.length() >= 2 && password.length() <=10))
            return -2;
        else if(traders.containsKey( name ))
            return -3;
        else {
            traders.put( name, temp );
            return 0;
        }
    }

    public int login( String name, String password )
    {
        if(!traders.containsKey( name ))
            return -1;
        else if(!traders.get( name ).getPassword().equals( password ))
            return -2;
        else if(loggedTraders.contains( traders.get( name ) )) 
            return -3;
        else {
            Trader temptrader = traders.get( name );
            temptrader.openWindow();
            if(temptrader.mailbox().size() == 0) {
                temptrader.receiveMessage("Welcome to SafeTrade!");
            }
            loggedTraders.add( temptrader );
            return 0;
        }
    }
    
    public void logout(Trader trader) {
        loggedTraders.remove( trader );
    }
    
    public void getQuote(String symbol, Trader trader) {
        String quote = exchange.getQuote(symbol);
        trader.receiveMessage(quote);
    }
    
    public void placeOrder(TradeOrder order) {
        exchange.placeOrder(order);
    }
    //
    // The following are for test purposes only
    //
    protected Map<String, Trader> getTraders()
    {
        return traders;
    }

    protected Set<Trader> getLoggedTraders()
    {
        return loggedTraders;
    }

    protected StockExchange getExchange()
    {
        return exchange;
    }

    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Brokerage.
     */
    public String toString()
    {
        String str = this.getClass().getName() + "[";
        String separator = "";

        Field[] fields = this.getClass().getDeclaredFields();

        for ( Field field : fields )
        {
            try
            {
                str += separator + field.getType().getName() + " "
                    + field.getName() + ":" + field.get( this );
            }
            catch ( IllegalAccessException ex )
            {
                System.out.println( ex );
            }

            separator = ", ";
        }

        return str + "]";
    }
}
