package com.olas.GetRichClicker.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository 
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserModel getUserByUsername(String username)
	{
		String getUser = "SELECT * FROM user WHERE username='"+username+"'";
		try
		{
			return jdbcTemplate.queryForObject(getUser, new UserRowMapper());
		}
		catch(EmptyResultDataAccessException e)
		{
			
		}
		
		return null;
	}
	
	public List<String> getUserRolesByUsername(String username)
	{
		String getUserRoles = "SELECT * FROM user_roles WHERE username='"+username+"'";
		List<String> roles = jdbcTemplate.query(getUserRoles , new UserRolesRowMapper());
		return roles;
	}
	
	public CoinsModel getCoinsByUsername(String username)
	{
		String getUserCoins = "SELECT * FROM coins WHERE username='"+username+"'";
		
		try
		{
			CoinsModel userCoins = jdbcTemplate.queryForObject(getUserCoins, new CoinsRowMapper());
			return userCoins;
		}
		catch(EmptyResultDataAccessException e)
		{
			
		}
		return null;
	}
	
	public List<BusinessModel> getBusiness()
	{
		String getBusinessList = "SELECT * FROM storeshop ORDER BY cost";
		List<BusinessModel> businessList = jdbcTemplate.query(getBusinessList , new BusinessRowMapper());
		return businessList;
	}
	
	public List<UserBusinessModel> getUserBusiness(String username)
	{
		String getBusinessList = "SELECT * FROM user_storeshop WHERE username='"+username+"'";
		List<UserBusinessModel> businessList = jdbcTemplate.query(getBusinessList , new UserBusinessRowMapper());
		return businessList;
	}
	
	public BusinessModel getBusinessByTitle(String businessTitle)
	{
		String getBusinessList = "SELECT * FROM storeshop WHERE title='"+businessTitle+"'";
		try
		{
			BusinessModel business = jdbcTemplate.queryForObject(getBusinessList, new BusinessRowMapper());
			return business;
		}
		catch(EmptyResultDataAccessException e)
		{
			
		}
		return null;
	}
	
	public UserBusinessModel getUserBusinessByTitle(String businessTitle, String username)
	{
		String getUserBusinessList = "SELECT * FROM user_storeshop WHERE business_title='"+businessTitle+"' AND username='"+username+"'";
		try
		{
			UserBusinessModel business = jdbcTemplate.queryForObject(getUserBusinessList, new UserBusinessRowMapper());
			return business;
		}
		catch(EmptyResultDataAccessException e)
		{
			
		}
		return null;
	}
	
	public UpgradesModel getUpgradeByTitle(String upgradeTitle)
	{
		String getUpgradeByTitle = "SELECT * FROM upgrades WHERE title='"+upgradeTitle+"'";
		try
		{
			UpgradesModel upgrade = jdbcTemplate.queryForObject(getUpgradeByTitle, new UpgradesRowMapper());
			return upgrade;
		}
		catch(EmptyResultDataAccessException e)
		{
			
		}
		return null;
	}
	
	public UserUpgradesModel getUserUpgradeByTitleID(String upgradeTitleID, String username)
	{
		String getUserUpgradesByTitle = "SELECT * FROM user_upgrades WHERE title_id='"+upgradeTitleID+"' AND username='"+username+"'";
		try
		{
			UserUpgradesModel upgrade = jdbcTemplate.queryForObject(getUserUpgradesByTitle, new UserUpgradesRowMapper());
			return upgrade;
		}
		catch(EmptyResultDataAccessException e)
		{
			
		}
		return null;
	}
	
	public UserUpgradesModel getUserUpgradeByTitle(String upgradeTitle, String username)
	{
		String getUserUpgradesByTitle = "SELECT * FROM user_upgrades WHERE title='"+upgradeTitle+"' AND username='"+username+"'";
		try
		{
			UserUpgradesModel upgrade = jdbcTemplate.queryForObject(getUserUpgradesByTitle, new UserUpgradesRowMapper());
			return upgrade;
		}
		catch(EmptyResultDataAccessException e)
		{
			
		}
		return null;
	}
	
	public List<UserUpgradesModel> getUserUpgradesByName(String username)
	{
		String getUpgrades = "SELECT * FROM user_upgrades WHERE username='"+username+"'";
		List<UserUpgradesModel> upgrades = jdbcTemplate.query(getUpgrades , new UserUpgradesRowMapper());
		return upgrades;
	}
	
	public List<UpgradesModel> getUpgrades()
	{
		String getUpgrades = "SELECT * FROM upgrades";
		List<UpgradesModel> upgrades = jdbcTemplate.query(getUpgrades , new UpgradesRowMapper());
		return upgrades;
	}
	
	public void registerNewUser(UserModel userModel)
	{
		String encodedPassword = passwordEncoder.encode( userModel.getPassword() );
		String registerNewUser = "INSERT INTO user VALUE (null,'"+userModel.getUsername()+"','"+userModel.getEmail()+"','"+encodedPassword+"')";
		jdbcTemplate.update( registerNewUser );
		
		String addRoles = "INSERT INTO user_roles VALUE (null,'"+userModel.getUsername()+"','ROLE_USER')";
		jdbcTemplate.update( addRoles );
		
		String addUserCoins = "INSERT INTO coins VALUE ('"+userModel.getUsername()+"',0,0)";
		jdbcTemplate.update( addUserCoins );
		
		String addUserBusiness = "INSERT INTO user_storeshop VALUES " +
								 "(null, '"+userModel.getUsername()+"','Cursor', 0, 1, 1),        "+
								 "(null, '"+userModel.getUsername()+"','Lemonade', 0, 1, 1),      "+
								 "(null, '"+userModel.getUsername()+"','Car wash', 0, 1, 1),      "+ 
								 "(null, '"+userModel.getUsername()+"','Pizzeria', 0, 1, 1),      "+
								 "(null, '"+userModel.getUsername()+"','Mine', 0, 1, 1),          "+
								 "(null, '"+userModel.getUsername()+"','Factory', 0, 1, 1),       "+
								 "(null, '"+userModel.getUsername()+"','Bank', 0, 1, 1),          "+
								 "(null, '"+userModel.getUsername()+"','Laboratory', 0, 1, 1),    "+
								 "(null, '"+userModel.getUsername()+"','Football club', 0, 1, 1), "+
								 "(null, '"+userModel.getUsername()+"','Spaceship', 0, 1, 1),     "+
								 "(null, '"+userModel.getUsername()+"','Oil company', 0, 1, 1),   "+
								 "(null, '"+userModel.getUsername()+"','Time machine', 0, 1, 1),  "+
								 "(null, '"+userModel.getUsername()+"','Wizard', 0, 1, 1),        "+
								 "(null, '"+userModel.getUsername()+"','Portal', 0, 1, 1),        "+
								 "(null, '"+userModel.getUsername()+"','Prism', 0, 1, 1)          ";
		jdbcTemplate.update( addUserBusiness );
	}
	
	public void addCoinAfterClick(String username)
	{
		String addCoin = "UPDATE coins SET coins=coins+1 WHERE username='"+username+"'";
		jdbcTemplate.update( addCoin );
	}
	
	public void buyUpgrades(String username, UpgradesModel upgrade)
	{
		String addUpgrade = "INSERT INTO user_upgrades VALUE (null,'"+username+"','"+upgrade.getTitle()+"','"+upgrade.getTitleID()+"')";
		jdbcTemplate.update( addUpgrade );
		String buyUpgrades = "UPDATE user_storeshop SET cps_multiplier=cps_multiplier*"+upgrade.getMultiplier()+" WHERE username='"+username+"' AND business_title='"+upgrade.getTitleID()+"'";
		jdbcTemplate.update( buyUpgrades );
	}
	
	public void clearProperties(String username)
	{
		String setCpsZero = "UPDATE coins SET cps = 0 WHERE username='"+username+"'";
		String setCoinsZero = "UPDATE coins SET coins = 0 WHERE username='"+username+"'";
		String setBoughtZero = "UPDATE user_storeshop SET bought = 0 WHERE username='"+username+"'";
		String setUpgradesMultiplierOne = "UPDATE user_storeshop SET cps_multiplier = 1 WHERE username='"+username+"'";
		String setMultiplierOne = "UPDATE user_storeshop SET multiplier = 1 WHERE username='"+username+"'";
		String deleteUpgrades = "DELETE FROM user_upgrades WHERE username='"+username+"'";
		
		jdbcTemplate.update( setCpsZero );
		jdbcTemplate.update( setCoinsZero );
		jdbcTemplate.update( setBoughtZero );
		jdbcTemplate.update( setUpgradesMultiplierOne );
		jdbcTemplate.update( setMultiplierOne );
		jdbcTemplate.update( deleteUpgrades );
	}
	
	public void buyBusiness(String businessTitle, String username)
	{
		String buyBusiness = "UPDATE user_storeshop SET bought=bought+1 WHERE business_title='"+businessTitle+"' AND username='"+username+"'";
		jdbcTemplate.update( buyBusiness );
	}
	
	public void removeCoinsAfterPayment(String username, double coinsToSubtract)
	{
		String removeCoins = "UPDATE coins SET coins=coins-"+coinsToSubtract+" WHERE username='"+username+"'";
		jdbcTemplate.update( removeCoins );
	}
	
	public void updateCps(BusinessModel businessModel, UserBusinessModel userBusiness, String username)
	{
		double cpsToAdd = businessModel.getCps() * userBusiness.getCpsMultiplier();
		
		String updateCps = "UPDATE coins SET cps=cps+"+cpsToAdd+" WHERE username='"+username+"'";
		jdbcTemplate.update( updateCps );
	}
	
	public void updateCpsByUpgrade(BusinessModel businessModel, UserBusinessModel userBusiness, String username, double upgradeMultiplier)
	{
		double cpsByBusiness = businessModel.getCps() * userBusiness.getBoughtNumber();
		double cpsToSubtract = cpsByBusiness * (userBusiness.getCpsMultiplier() / upgradeMultiplier);
		double cpsToAdd = cpsByBusiness * userBusiness.getCpsMultiplier();
		double cpsToReturn = cpsToAdd - cpsToSubtract;
		
		String updateCps = "UPDATE coins SET cps=cps+"+cpsToReturn+" WHERE username='"+username+"'";
		jdbcTemplate.update( updateCps );
	}
	
	public void addCoinsPerSecond(String username, double coinsToAdd)
	{
		String addCoins = "UPDATE coins SET coins="+coinsToAdd+" WHERE username='"+username+"'";
		jdbcTemplate.update(addCoins);
	}
	
	public void addToMultiplier(String username, String businessTitle)
	{
		String updateMultiplier = "UPDATE user_storeshop SET multiplier=multiplier*1.3 WHERE username='"+username+"' AND business_title='"+businessTitle+"'";
		jdbcTemplate.update(updateMultiplier);
	}
	
	// ---------- ROW MAPPERS ---------- \\ 
	private class UserRowMapper implements RowMapper<UserModel>
	{ 
		public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException 
		{ 
			UserModel user = new UserModel(); 
			user.setUsername( rs.getString("username") );
			user.setEmail( rs.getString("email") );
			user.setPassword( rs.getString("password") );
			return user; 
		} 
	}
	
	private class CoinsRowMapper implements RowMapper<CoinsModel>
	{ 
		public CoinsModel mapRow(ResultSet rs, int rowNum) throws SQLException 
		{ 
			CoinsModel userCoins = new CoinsModel(); 
			userCoins.setUsername( rs.getString("username") );
			userCoins.setCoins(  Double.parseDouble(rs.getString("coins")) );
			userCoins.setCps( Double.parseDouble(rs.getString("cps")) );
			return userCoins; 
		} 
	}
	
	private class BusinessRowMapper implements RowMapper<BusinessModel>
	{ 
		public BusinessModel mapRow(ResultSet rs, int rowNum) throws SQLException 
		{ 
			BusinessModel business = new BusinessModel(); 
			business.setTitle( rs.getString("title") );
			business.setPrice(  Long.parseLong(rs.getString("cost")) );
			business.setCps(  Long.parseLong(rs.getString("cps")) );
			return business; 
		} 
	}
	
	private class UserBusinessRowMapper implements RowMapper<UserBusinessModel>
	{ 
		public UserBusinessModel mapRow(ResultSet rs, int rowNum) throws SQLException 
		{ 
			UserBusinessModel business = new UserBusinessModel(); 
			business.setUsername( rs.getString("username") );
			business.setBusinessTitle(  rs.getString("business_title") );
			business.setBoughtNumber(  Long.parseLong(rs.getString("bought")) );
			business.setMultiplier(  Double.parseDouble(rs.getString("multiplier")) );
			business.setCpsMultiplier( Double.parseDouble(rs.getString("cps_multiplier")) );
			return business; 
		}
	}
	
	private class UpgradesRowMapper implements RowMapper<UpgradesModel>
	{ 
		public UpgradesModel mapRow(ResultSet rs, int rowNum) throws SQLException 
		{ 
			UpgradesModel upgrades = new UpgradesModel(); 
			upgrades.setTitle( rs.getString("title") );
			upgrades.setTitleID( rs.getString("title_id") );
			upgrades.setPrice(  Long.parseLong(rs.getString("cost")) );
			upgrades.setMultiplier(  Float.parseFloat(rs.getString("multiplier")) );
			upgrades.setOperation(  rs.getString("operation") );
			return upgrades; 
		}
	}	
	
	private class UserUpgradesRowMapper implements RowMapper<UserUpgradesModel>
	{ 
		public UserUpgradesModel mapRow(ResultSet rs, int rowNum) throws SQLException 
		{ 
			UserUpgradesModel upgrades = new UserUpgradesModel(); 
			upgrades.setUsername( rs.getString("username") );
			upgrades.setTitle( rs.getString("title") );
			upgrades.setTitleID( rs.getString("title_id") );
			return upgrades; 
		}
	}
	
	private class UserRolesRowMapper implements RowMapper<String>
	{
		public String mapRow(ResultSet rs, int rowNum) throws SQLException 
		{ 
			return rs.getString("role");
		} 
	}
}
