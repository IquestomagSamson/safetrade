import java.awt.GraphicsConfiguration;
import java.lang.reflect.*;
import java.util.*;
/**
 * @author Weitung Chen
 */
/**
 * Represents a stock trader.
 */
public class Trader implements Comparable<Trader>
{
    private Brokerage brokerage;

    private String screenName, password;

    private TraderWindow myWindow;

    private Queue<String> mailbox;


    public Trader( Brokerage brokerage, java.lang.String name, java.lang.String pswd )
    {
        this.brokerage = brokerage;
        this.screenName = name;
        this.password = pswd;
        mailbox = new LinkedList<String>();
    }


    //
    // The following are for test purposes only
    //
    protected Queue<String> mailbox()
    {
        return mailbox;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Trader.
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
                if ( field.getType().getName().equals( "Brokerage" ) )
                    str += separator + field.getType().getName() + " " + field.getName();
                else
                    str += separator + field.getType().getName() + " " + field.getName() + ":" + field.get( this );
            }
            catch ( IllegalAccessException ex )
            {
                System.out.println( ex );
            }

            separator = ", ";
        }

        return str + "]";
    }


    @Override
    public int compareTo( Trader o )
    {
        return this.getName().compareToIgnoreCase( o.getName() );
    }


    public void quit()
    {
        brokerage.logout( this );
    }


    public void getQuote( String symbol )
    {
        brokerage.getQuote( symbol, this );
    }


    public void placeOrder( TradeOrder tradeOrder )
    {
        brokerage.placeOrder( tradeOrder );

    }


    public String getName() // not sure
    {
        return screenName;
    }


    // public GraphicsConfiguration getName() // given
    // {
    // return screenName;
    // }

    public String getPassword()
    {
        return password;
    }


    public boolean equals( Trader other )
    {
        return this.screenName.compareToIgnoreCase( other.getName() ) == 0;
    }


    public void openWindow()
    {
        myWindow = new TraderWindow( this );
        while ( !mailbox.isEmpty() )
        {
            myWindow.showMessage( mailbox.remove() );
        }
    }


    public boolean hasMessages()
    {
        return !mailbox.isEmpty();
    }


    public void receiveMessage( String msg )
    {
        mailbox.add( msg );
        if ( myWindow != null )
        {
            while(!mailbox.isEmpty())
                myWindow.showMessage( mailbox.remove() );
        }
    }
    }