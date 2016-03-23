import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.*;

import org.junit.*;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;


/**
 * SafeTrade tests: TradeOrder PriceComparator Trader Brokerage StockExchange
 * Stock
 *
 * @author Weitung Chen, Jessica Zheng, Kushal Tirumala
 * @version 3/23/2016
 * @author Assignment: JM Chapter 19 - SafeTrade
 * 
 * @author Sources: Jessica, Kushal, Weitung Chen
 *
 */
public class JUSafeTradeTest
{
    // --Test TradeOrder
    /**
     * TradeOrder tests: TradeOrderConstructor - constructs TradeOrder and then
     * compare toString TradeOrderGetTrader - compares value returned to
     * constructed value TradeOrderGetSymbol - compares value returned to
     * constructed value TradeOrderIsBuy - compares value returned to
     * constructed value TradeOrderIsSell - compares value returned to
     * constructed value TradeOrderIsMarket - compares value returned to
     * constructed value TradeOrderIsLimit - compares value returned to
     * constructed value TradeOrderGetShares - compares value returned to
     * constructed value TradeOrderGetPrice - compares value returned to
     * constructed value TradeOrderSubtractShares - subtracts known value &
     * compares result returned by getShares to expected value
     */
    private String symbol = "GGGL";

    private boolean buyOrder = true;

    private boolean marketOrder = true;

    private int numShares = 123;

    private int numToSubtract = 24;

    private double price = 123.45;


    @Test
    public void tradeOrderConstructor()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder, numShares, price );
        String toStr = to.toString();

        assertTrue( "<< Invalid TradeOrder Constructor >>",
            toStr.contains( "TradeOrder[Trader trader:null" ) && toStr.contains( "java.lang.String symbol:" + symbol )
                && toStr.contains( "boolean buyOrder:" + buyOrder )
                && toStr.contains( "boolean marketOrder:" + marketOrder )
                && toStr.contains( "int numShares:" + numShares ) && toStr.contains( "double price:" + price ) );
    }


    @Test
    public void TradeOrderToString()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder, numShares, price );
        assertNotNull( to.toString() );
    }


    @Test
    public void tradeOrderGetTrader()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder, numShares, price );
        assertNull( "<< TradeOrder: " + to.getTrader() + " should be null >>", to.getTrader() );
    }


    @Test
    public void tradeOrderGetSymbol()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder, numShares, price );
        assertEquals( "<< TradeOrder: " + to.getTrader() + " should be " + symbol + " >>", symbol, to.getSymbol() );
    }


    @Test
    public void tradeOrderIsBuy()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder, numShares, price );

        assertTrue( "<< TradeOrder: " + to.isBuy() + " should be " + buyOrder + " >>", to.isBuy() );
    }


    @Test
    public void tradeOrderIsSell()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder, numShares, price );
        assertFalse( "<< TradeOrder: " + to.isSell() + " should be " + !buyOrder + " >>", to.isSell() );
    }


    @Test
    public void tradeOrderIsMarket()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder, numShares, price );
        assertTrue( "<< TradeOrder: " + to.isMarket() + " should be " + marketOrder + " >>", to.isMarket() );
    }


    @Test
    public void tradeOrderIsLimit()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder, numShares, price );

        assertFalse( "<< TradeOrder: " + to.isLimit() + " should be " + !marketOrder + ">>", to.isLimit() );
    }


    @Test
    public void tradeOrderGetShares()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder, numShares, price );
        assertTrue( "<< TradeOrder: " + to.getShares() + " should be " + numShares + ">>",
            numShares == to.getShares() || ( numShares - numToSubtract ) == to.getShares() );
    }


    @Test
    public void tradeOrderGetPrice()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder, numShares, price );
        assertEquals( "<< TradeOrder: " + to.getPrice() + " should be " + price + ">>", price, to.getPrice(), 0.0 );
    }


    @Test
    public void tradeOrderSubtractShares()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder, numShares, price );
        to.subtractShares( numToSubtract );
        assertEquals(
            "<< TradeOrder: subtractShares(" + numToSubtract + ") should be " + ( numShares - numToSubtract ) + ">>",
            numShares - numToSubtract,
            to.getShares() );
    }


    // --Test TraderWindow Stub
    @Test
    public void traderWindowConstructor()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
    }


    @Test
    public void traderWindowShowMessage()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
        tw.showMessage( null );
    }


    // --Test PriceComparator
    /**
     * --priceComparatorConstructor: constructs PriceComparator and compares to
     * see if an object was created
     * 
     * --priceComparatorCompare: compares two NON market orders w/ the same
     * price
     * 
     */
    // --Test PriceComparator
    @Test
    public void priceComparatorConstructor1()
    {
        PriceComparator pc = new PriceComparator();
        assertNotNull( pc );
    }


    @Test
    public void priceComparatorConstructor2()
    {
        PriceComparator pc = new PriceComparator( false );
        assertNotNull( pc );
    }


    @Test
    public void priceComparatorCompare()
    {
        TradeOrder to1 = new TradeOrder( null, symbol, buyOrder, !marketOrder, numShares, 123.45 );
        TradeOrder to2 = new TradeOrder( null, symbol, buyOrder, !marketOrder, numShares, 100 );
        PriceComparator pc = new PriceComparator( false );
        assertEquals( "<< PriceComparator: compare(" + to1.getPrice() + ", " + to2.getPrice() + ") should be "
            + ( to1.getPrice() - to2.getPrice() ) * 100 + ">>", 2345, pc.compare( to2, to1 ) );

    }


    // --Test Trader
    // --Test Trader
    @Test
    public void traderConstructor()
    {
        Trader t = new Trader( new Brokerage( new StockExchange() ), "Trader1", "1234" );
        assertNotNull( t );
    }


    @Test
    public void traderToString()
    {
        Trader t = new Trader( new Brokerage( new StockExchange() ), "Trader1", "1234" );
        assertNotNull( t.toString() );
    }


    @Test
    public void traderCompareTo()
    {
        Trader t1 = new Trader( new Brokerage( new StockExchange() ), "Trader1", "1234" );
        Trader t2 = new Trader( new Brokerage( new StockExchange() ), "Trader2", "4321" );
        assertEquals( "<< Trader: " + t1.getName() + " compareTo ( " + t2.getName() + " ) should be "
            + t1.getName().compareToIgnoreCase( t2.getName() ) + " >>", t1.compareTo( t2 ), -1 );
    }


    @Test
    public void traderQuit()
    {
        Brokerage bk = new Brokerage( new StockExchange() );
        Trader t = new Trader( bk, "Trader1", "1234" );
        bk.addUser( "Trader1", "1234" );
        bk.login( "Trader1", "1234" );
        t.quit();
        assertEquals( bk.getLoggedTraders().size(), 0 );
    }


    @Test
    public void traderGetQuote()
    {
        StockExchange se = new StockExchange();
        se.listStock( symbol, "Trader", price );
        Trader t = new Trader( new Brokerage( se ), "Trader1", "1234" );
        t.getQuote( symbol );
        assertTrue( "<< Trader: getQuote( " + symbol + " ) should be " + t.mailbox().peek() + " >>", t.hasMessages() );
    }


    @Test
    public void traderPlaceOrder()
    {
        StockExchange se = new StockExchange();
        se.listStock( symbol, "Trader", price );
        Trader t = new Trader( new Brokerage( se ), "Trader1", "1234" );
        t.placeOrder( new TradeOrder( t, symbol, buyOrder, marketOrder, numShares, price ) );
        assertTrue( "<< Trader: placeOrder( " + symbol + " ) should be " + true + " >>", t.hasMessages() );
    }


    @Test
    public void traderGetName()
    {
        Trader t = new Trader( new Brokerage( new StockExchange() ), "Trader1", "1234" );
        assertEquals( "<< Trader: " + t.getName() + " should be Trader1 >>", t.getName(), "Trader1" );
    }


    @Test
    public void traderGetPassword()
    {
        Trader t = new Trader( new Brokerage( new StockExchange() ), "Trader1", "1234" );
        assertEquals( "<< Trader: " + t.getPassword() + " should be 1234 >>", t.getPassword(), "1234" );
    }


    @Test
    public void traderEquals()
    {
        Trader t1 = new Trader( new Brokerage( new StockExchange() ), "Trader1", "1234" );
        Trader t2 = new Trader( new Brokerage( new StockExchange() ), "Trader2", "4321" );
        assertFalse( "<< Trader: " + t1.equals( t2 ) + " should be " + false + " >>", t1.equals( t2 ) );
    }


    @Test
    public void traderOpenWindow()
    {
        Trader t = new Trader( new Brokerage( new StockExchange() ), "Trader1", "1234" );
        t.receiveMessage( "Hi" );
        t.openWindow();
        assertFalse( "<< Trader: " + t.mailbox().size() + " should be " + 0 + " >>", t.hasMessages() );
    }


    @Test
    public void traderHasMessages()
    {
        Trader t = new Trader( new Brokerage( new StockExchange() ), "Trader1", "1234" );
        assertTrue( "<< Trader: " + t.mailbox().size() + " should be " + 0 + " >>", t.mailbox().isEmpty() );
    }


    @Test
    public void traderReceiveMessage()
    {
        Trader t = new Trader( new Brokerage( new StockExchange() ), "Trader1", "1234" );
        t.receiveMessage( "Hi" );
        assertEquals( "<< Trader: " + t.mailbox().peek() + " should be " + "Hi" + " >>", t.mailbox().remove(), "Hi" );
    }

    // --Test Brokerage
    /**
     * Tests for Brokerage
     * 
     * --brokerageConstructor: --brokerageAddUser: --brokerageLogin:
     * --brokerageLogout: --brokerageGetQuote: --brokeragePlaceOrder:
     * --brokerageGetTraders: --brokerageGetLoggedTraders:
     * --brokerageGetExchange:
     */

    public StockExchange s = new StockExchange();

    String name = "trader1";

    String password = "traderone";


    @Test
    public void brokerageConstructor()
    {
        Brokerage temp = new Brokerage( s );
        assertNotNull( "<< Invalid Brokerage Constructor >>", temp );
    }


    @Test
    public void brokerageAddUser()
    {
        Brokerage temp = new Brokerage( s );
        temp.addUser( name, password );
        assertTrue(
            "<< Brokerage: Add user should add a new user with the name" + name + " and password " + password + ">>",
            temp.getTraders().containsKey( "trader1" ) );
    }


    @Test
    public void brokerageLogin()
    {
        Brokerage temp = new Brokerage( s );
        Trader t = new Trader( temp, name, password );
        temp.addUser( name, password );
        temp.login( name, password );
        assertTrue(
            "<< Brokerage: login should login a new trader with name" + name + " and password " + password + " >>",
            temp.getLoggedTraders().contains( t ) );
    }


    @Test
    public void brokerageLogOut()
    {
        Brokerage temp = new Brokerage( s );
        temp.addUser( name, password );
        temp.login( name, password );
        Trader t = new Trader( temp, name, password );
        temp.logout( t );
        assertFalse(
            "<< Brokerage: logout should log out a user with name" + name + " and password " + password + " >>",
            temp.getLoggedTraders().contains( t ) );
    }


    @Test
    public void brokerageGetQuote()
    {
        StockExchange t = new StockExchange();
        t.listStock( symbol, "GGL", price );
        Brokerage temp = new Brokerage( t );
        Trader temptrader = new Trader( temp, name, password );
        temptrader.getQuote( symbol );
        temp.addUser( name, password );
        temp.getQuote( symbol, temp.getTraders().get( name ) );
        String s = temptrader.mailbox().remove();
        assertEquals( "<< Brokerage: getquote(" + symbol + ") should return " + s,
            s,
            temp.getTraders().get( name ).mailbox().peek() );
    }


    @Test
    public void brokeragePlaceOrder()
    {
        StockExchange t = new StockExchange();
        Brokerage temp = new Brokerage( t );
        t.listStock( symbol, "Giggle", price );
        temp.addUser( name, password );
        Trader trader = temp.getTraders().get( name );
        TradeOrder torder = new TradeOrder( temp.getTraders().get( name ),
            symbol,
            buyOrder,
            marketOrder,
            numShares,
            price );
        temp.placeOrder( torder );
        assertEquals( "<< Brokerage: placeorder(" + torder.toString() + ") should return " + s,
            trader.mailbox().peek(),
            "New order:  Buy GGGL (Giggle)\n123 shares at market" );
    }


    @Test
    public void brokerageGetTraders()
    {
        Brokerage temp = new Brokerage( s );
        assertNotNull( "<< Brokerage: getTraders should return the list of traders >>", temp.getTraders() );
    }


    @Test
    public void brokerageGetLoggedTraders()
    {
        Brokerage temp = new Brokerage( s );
        assertNotNull( "<< Brokerage: getLoggedTrader should return the list of logged traders >>",
            temp.getLoggedTraders() );
    }


    @Test
    public void brokerageGetExchange()
    {
        Brokerage temp = new Brokerage( s );
        assertEquals( "<< Brokerage: getExchange shoudl return" + s.toString(), temp.getExchange(), s );
    }


    @Test
    public void brokerageToString()
    {
        Brokerage temp = new Brokerage( s );
        assertNotNull( "<< Brokerage: toString() should return the string version of the brokerage", temp.toString() );
    }

    // --Test StockExchange

    /**
     * 
     * --stockexchangeConstructor --stockexchangeListStock
     * --stockexchangeGetQuote --stockexchangeGetListedStocks
     * --stockExchangeToString
     */

    String company = "giggle.com";


    @Test
    public void stockexchangeConstructor()
    {
        StockExchange s = new StockExchange();
        assertTrue( "<< Invalid StockExchange Constructor >>",
            s.toString().contains( "java.util.Map listedStocks:{}" ) && s.toString().contains( "StockExchange[" ) );
    }


    @Test
    public void stockexchangeListStock()
    {
        StockExchange s = new StockExchange();
        s.listStock( symbol, company, price );
        assertTrue( s.getListedStocks().containsKey( symbol ) );
    }


    @Test
    public void stockexchangeGetQuote()
    {
        StockExchange s = new StockExchange();
        Stock stock = new Stock( symbol, "giggle.com", price );
        String temp = stock.getQuote();
        s.listStock( symbol, company, price );
        assertEquals( temp, s.getQuote( symbol ) );
    }


    @Test
    public void stockexchangeGetListedStocks()
    {
        StockExchange s = new StockExchange();
        assertNotNull( s.getListedStocks() );
    }


    @Test
    public void stockexchangeToString()
    {
        StockExchange s = new StockExchange();
        assertNotNull( s.toString() );
    }

    // --Test Stock
    private String companyname = "Giggle.com";


    @Test
    public void stockConstructor()
    {
        Stock s = new Stock( symbol, companyname, price );
        assertNotNull( s );
    }


    @Test
    public void stockExecuteOrdersTest()
    {
        Stock s = new Stock( symbol, companyname, price );
        Trader t = new Trader( new Brokerage( new StockExchange() ), "trader1", "password" );
        TradeOrder order = new TradeOrder( t, symbol, buyOrder, marketOrder, numShares, price );
        s.placeOrder( order );
        Trader t2 = new Trader( new Brokerage( new StockExchange() ), "trader1", "password" );
        TradeOrder order2 = new TradeOrder( t2, symbol, !buyOrder, marketOrder, numShares, price );
        s.placeOrder( order2 );
        assertTrue( "<< Invalid Stock executeOrders >>", s.getVolume() != 0 );
    }


    @Test
    public void stockGetQuoteTest()
    {
        Stock s = new Stock( symbol, companyname, price );
        String quote = s.getQuote();
        assertTrue( "<< Invalid Stock getQuote >>",
            quote.contains( s.getCompanyName() ) && quote.contains( s.getStockSymbol() )
                && quote.contains( "Price: " + s.getLastPrice() ) && quote.contains( "hi: " + s.getHiPrice() )
                && quote.contains( "lo: " + s.getLoPrice() ) && quote.contains( "vol: " + s.getVolume() )
                && quote.contains( "Ask:" ) && quote.contains( "Bid:" ) );
    }


    @Test
    public void stockPlaceOrderTest()
    {
//        Stock s = new Stock( symbol, companyname, price );
//        Trader t = new Trader( new Brokerage( new StockExchange() ), "trader1", "password" );
//
//        TradeOrder order = new TradeOrder( t, symbol, buyOrder, marketOrder, numShares, price );
//        s.placeOrder( order );
        StockExchange s = new StockExchange();
        s.listStock( symbol, companyname, price );
        Brokerage temp = new Brokerage(s);
        Trader t = new Trader(temp, "kush", "tirumala");
        Queue<String> mailbox = t.mailbox();
        TradeOrder order = new TradeOrder(t, symbol, buyOrder, marketOrder, numShares, price);
        t.placeOrder( order );
        assertFalse( "<< Invalid Stock placeOrder >>", mailbox.isEmpty() );
    }


    @Test
    public void stockGetBuyOrdersTest()
    {
        Stock s = new Stock( symbol, companyname, price );
        assertNotNull( s.getBuyOrders() );
    }


    @Test
    public void stockGetSellOrdersTest()
    {
        Stock s = new Stock( symbol, companyname, price );
        assertNotNull( s.getSellOrders() );
    }


    @Test
    public void stockToStringTest()
    {
        Stock s = new Stock( "GGL", "Giggle.com", 38.00 );
        assertNotNull( s.toString() );
    }


    // Remove block comment below to run JUnit test in console

    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JUSafeTradeTest.class );
    }


    public static void main( String args[] )
    {
        org.junit.runner.JUnitCore.main( "JUSafeTradeTest" );
    }

}
