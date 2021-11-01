package pt.uc.dei.paj.dao;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pt.uc.dei.paj.dto.StatsDto;
import pt.uc.dei.paj.entity.Channel;
import pt.uc.dei.paj.entity.User;
import pt.uc.dei.paj.util.Constants;

//@RunWith(Arquillian.class)
public class ChannelDaoTest {
	
	/*
	 * @Deployment public static Archive<?> criarArquivoTeste() { Archive<?>
	 * arquivoTeste = ShrinkWrap.create(WebArchive.class, "proj6_data_server.war")
	 * .addPackage(ChannelDao.class.getPackage()) .addClass(Channel.class)
	 * .addAsResource("META-INF/persistence.xml")
	 * .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml"); return arquivoTeste;
	 * }
	 * 
	 * @Inject private ChannelDao channelDao;
	 * 
	 * @Test
	 * 
	 * @InSequence(1)
	 * 
	 * @Transactional public void testInsert() throws Exception { final Channel
	 * channel = new Channel(); channel.setTitle(Constants.CONVERSATION);
	 * channel.setActive(true); channel.setDirect(true);
	 * channel.setCreatedDate(LocalDateTime.now()); channel.setWorkspace(null);
	 * channel.setUser(null);
	 * 
	 * channelDao.persist(channel);
	 * 
	 * Assert.assertNotNull(channel); System.out.println("Inserted Channel id = " +
	 * channel.getId()); }
	 * 
	 * @Test
	 * 
	 * @InSequence(2)
	 * 
	 * @Transactional public void testSelectAllChannels() {
	 * 
	 * final ArrayList<Channel> channelList = channelDao.findAllChannels();
	 * 
	 * System.out.println("testFindAllChannels size = " + channelList.size());
	 * 
	 * Assert.assertEquals(1, channelList.size());
	 * 
	 * }
	 * 
	 * @Test
	 * 
	 * @InSequence(3) public void testSelectChannelByName() {
	 * 
	 * final Channel cha = channelDao.findById(1);
	 * 
	 * Assert.assertNotNull(cha);
	 * 
	 * System.out.println("testSelectByChannelName id = " + cha.getId());
	 * 
	 * }
	 */
}