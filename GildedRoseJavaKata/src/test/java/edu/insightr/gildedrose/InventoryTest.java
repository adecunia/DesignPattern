package edu.insightr.gildedrose;

import org.junit.After;
        import org.junit.Before;
        import org.junit.Test;

        import java.util.concurrent.ExecutionException;

        import static org.hamcrest.core.Is.is;
        import static org.junit.Assert.assertThat;

        import static org.junit.Assert.*;

public class InventoryTest {
    protected Inventory inv;
    Item[] ancienneListeDesItems;

    @Before
    public void setUp()
    {
        inv = new Inventory();
        ancienneListeDesItems = inv.getItems();
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testUpdateQualityWhenSellInFinished() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for(int i = 0;i < items.length;i ++)
        {
            if (ancienneListeDesItems[i].getSellIn() == 0)
                assertEquals(ancienneListeDesItems[i].getQuality() - 2,items[i].getQuality());
        }
    }

    @Test
    public void testQualityIsNeverNegative() throws Exception
    {
        Item[] items;
        int nbIterations = 10;
        for (int i=0;i < nbIterations;i++)
        {
            inv.updateQuality();
            items = inv.getItems();
            for (int j=0;j < items.length;j++)
            {
                assertTrue(items[j].getQuality() >= 0);
            }
        }
    }

    @Test
    public void testQualityAgedBrie() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for (int i=0;i < items.length;i++)
        {
            if (items[i].getName().equals("Aged Brie"))
            {
                assertTrue(items[i].getQuality() >= ancienneListeDesItems[i].getQuality());
            }
        }
    }

    @Test
    public void testQualityNotMoreThan50() throws Exception
    {
        Item[] items;
        int nbIterations = 10;
        for (int i=0;i < nbIterations;i++)
        {
            inv.updateQuality();
            items = inv.getItems();
            for (int j=0;j < items.length;j++)
            {
                assertTrue(items[j].getQuality() <= 50);
            }
        }
    }

    @Test
    public void testSulfurasNotDecreaseInQuality() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for (int i=0;i < items.length;i++)
        {
            if (items[i].getName().equals("Sulfuras, Hand of Ragnaros"))
            {
                assertFalse(items[i].getQuality() < ancienneListeDesItems[i].getQuality());
            }
        }
    }

    @Test
    public void testSulfurasNeverHasToBeSold() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for (int i=0;i < items.length;i++)
        {
            if (items[i].getName().equals("Sulfuras, Hand of Ragnaros"))
            {
                assertFalse(items[i].getSellIn() < ancienneListeDesItems[i].getSellIn());
            }
        }
    }

    @Test
    public void testSulfurasQualityIs80() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for (int i=0;i < items.length;i++)
        {
            if (items[i].getName().equals("Sulfuras, Hand of Ragnaros"))
            {
                assertTrue(items[i].getQuality() == 80);
            }
        }
    }

    @Test
    public void testBackStagePassesQualityIncreasesBy2When10OrLess() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for (int i=0;i < items.length;i++)
        {
            if (items[i].getName().equals("Backstage passes to a TAFKAL80ETC concert") && items[i].getSellIn() <= 10)
            {
                assertEquals(items[i].getQuality(),ancienneListeDesItems[i].getQuality() + 2);
            }
        }
    }

    @Test
    public void testBackStagePassesQualityIncreasesBy3When5OrLess() throws Exception
    {
        inv.updateQuality();
        Item[] items = inv.getItems();
        for (int i=0;i < items.length;i++)
        {
            if (items[i].getName().equals("Backstage passes to a TAFKAL80ETC concert") && items[i].getSellIn() <= 5)
            {
                assertEquals(items[i].getQuality(),ancienneListeDesItems[i].getQuality() + 3);
            }
        }
    }

    @Test
    public void qualityCheck() throws Exception
    {
        Inventory inventory = new Inventory();
        Item[] items = inventory.getItems();

        Item itemConjured = items[5];
        assertThat(itemConjured.getName(),is("Conjured Mana Cake"));
        assertThat(itemConjured.getQuality(),is(6));
        inventory.updateQuality();
        assertThat(itemConjured.getQuality(),is(4));
    }

}