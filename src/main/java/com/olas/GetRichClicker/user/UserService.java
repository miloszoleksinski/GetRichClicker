package com.olas.GetRichClicker.user;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService 
{
	@Autowired
    private UserRepository userRepository;
	
	public UserModel getUserByUsername(String username)
	{
		return userRepository.getUserByUsername(username);
	}
	
	public List<String> getUserRolesByUsername(String username)
	{
		return userRepository.getUserRolesByUsername(username);
	}
	
	public CoinsModel getCoinsByUsername(String username)
	{
		return userRepository.getCoinsByUsername(username);
	}
	
	public List<BusinessModel> getBusiness()
	{
		return userRepository.getBusiness();
	}
	
	public List<UserBusinessModel> getUserBusiness(String username)
	{
		return userRepository.getUserBusiness(username);
	}
	
	public BusinessModel getBusinessByTitle(String businessTitle)
	{
		return userRepository.getBusinessByTitle(businessTitle);
	}
	
	public UserBusinessModel getUserBusinessByTitle(String businessTitle, String username)
	{
		return userRepository.getUserBusinessByTitle(businessTitle, username);
	}
	
	public List<UserUpgradesModel> getUserUpgradesByName(String username)
	{
		return userRepository.getUserUpgradesByName(username);
	}
	
	public List<UpgradesModel> getUpgrades(String username)
	{
		List<UpgradesModel> upgrades = userRepository.getUpgrades();
		List<UserUpgradesModel> userUpgrades = this.getUserUpgradesByName(username);
		List<UpgradesModel> listToReturn = new ArrayList<UpgradesModel>();
		
		if( userUpgrades.size() > 0 )
		{
			boolean isBought = false;
			
			for(int i=0; i<upgrades.size(); i++)
			{
				isBought = false;
				
				for(int j=0; j<userUpgrades.size(); j++)
				{
					if( !upgrades.get(i).getTitle().contains(userUpgrades.get(j).getTitle()) ) { isBought = true; }
					else{ isBought = false; break; }
				}
				
				if( isBought == true ){ listToReturn.add( upgrades.get(i) ); }
			}
			return listToReturn;
		}
		else { return upgrades; }
	}
	
	public UpgradesModel getUpgradeByTitle(String upgradeTitle)
	{
		return userRepository.getUpgradeByTitle(upgradeTitle);
	}
	
	public UserUpgradesModel getUserUpgradeByTitleID(String upgradeTitleID, String username)
	{
		return userRepository.getUserUpgradeByTitleID(upgradeTitleID, username);
	}
	
	public UserUpgradesModel getUserUpgradeByTitle(String upgradeTitle, String username)
	{
		return userRepository.getUserUpgradeByTitle(upgradeTitle, username);
	}
	
	public void registerNewUser(UserModel userModel)
	{
		userRepository.registerNewUser(userModel);
	}
	
	public void addCoinAfterClick(String username)
	{
		userRepository.addCoinAfterClick(username);
	}
	
	public void buyUpgrades(UpgradesModel upgrade, String username)
	{
		userRepository.buyUpgrades(username, upgrade);
	}
	
	public void clearProperties(String username)
	{
		userRepository.clearProperties(username);
	}
	
	public void buyBusiness(String businessTitle, String username)
	{
		userRepository.buyBusiness(businessTitle, username);
	}
	
	public void updateCps(String businessTitle, String username)
	{
		BusinessModel businessModel = this.getBusinessByTitle(businessTitle);
		UserBusinessModel userBusiness = this.getUserBusinessByTitle(businessTitle, username);
		
		userRepository.updateCps(businessModel, userBusiness, username);
	}
	
	public void updateCpsByUpgrade(String businessTitle, double upgradeMultiplier, String username)
	{
		BusinessModel businessModel = this.getBusinessByTitle(businessTitle);
		UserBusinessModel userBusiness = this.getUserBusinessByTitle(businessTitle, username);
		
		userRepository.updateCpsByUpgrade(businessModel, userBusiness, username, upgradeMultiplier);
	}
	
	public void removeCoinsAfterPayment(String username, double coinsToSubtract)
	{
		userRepository.removeCoinsAfterPayment(username, coinsToSubtract);
	}
	
	public void addCoinsPerSecond(String username, double coinsToAdd)
	{
		userRepository.addCoinsPerSecond(username, coinsToAdd);
	}
	
	public void addToMultiplier(String username, String businessTitle)
	{
		userRepository.addToMultiplier(username, businessTitle);
	}
	
	public List<String> getUpgradePrices(List<UpgradesModel> upgrades)
	{
		List<String> prices = new ArrayList<String>();
		for(int i=0; i<upgrades.size(); i++)
		{
			prices.add( this.coinsFormatter(upgrades.get(i).getPrice()) );
		}
		return prices;
	}
	
	public List<String> multiplierAndPriceList(List<BusinessModel> business, List<UserBusinessModel> userBusiness)
	{
		List<String> businessPrice = new ArrayList<String>();
		for(int i=0; i<business.size(); i++)
		{
			businessPrice.add( this.coinsFormatter(business.get(i).getPrice() * userBusiness.get(i).getMultiplier()) );
		}
		return businessPrice;
	}
	
	public List<String> FormatCps(List<BusinessModel> businessList, List<UserBusinessModel> userBusinessList)
	{
		List<String> cpsList = new ArrayList<String>();
		for(int i=0; i<businessList.size(); i++)
		{
			cpsList.add( this.coinsFormatter( businessList.get(i).getCps() * userBusinessList.get(i).getCpsMultiplier()) );
		}
		return cpsList;
	}
	
	public String coinsFormatter(double priceAndMultiplier)
	{
		Locale.setDefault(new Locale("pl", "PL"));
		DecimalFormat df = new DecimalFormat("###,###.###");
		df.setRoundingMode(RoundingMode.DOWN);
		
		double number = priceAndMultiplier;
		
		String numeral = "";
		if	   ( number >= 1000000000000000L ) { numeral = "quadrillion"; number /= 1000000000000000L; }
		else if( number >= 1000000000000L )    { numeral = "trillion";    number /= 1000000000000L; }
		else if( number >= 1000000000 ) 	   { numeral = "billion";  	  number /= 1000000000; }
		else if( number >= 1000000 ) 		   { numeral = "million";     number /= 1000000; }
		else if( number >= 1000 ) 			   { numeral = "thousand"; 	  number /= 1000; }
		
		return df.format( number ) + " " + numeral;
	}
	
	public String mainCoinsFormatter(double priceAndMultiplier)
	{
		Locale.setDefault(new Locale("pl", "PL"));
		DecimalFormat df = new DecimalFormat("###,###.#");
		df.setRoundingMode(RoundingMode.DOWN);
		
		double number = priceAndMultiplier;
		
		String numeral = "";
		if	   ( number >= 1000000000000000L ) { numeral = "quadrillion"; number /= 1000000000000000L; }
		else if( number >= 1000000000000L )    { numeral = "trillion";    number /= 1000000000000L; }
		else if( number >= 1000000000 ) 	   { numeral = "billion";  	  number /= 1000000000; }
		else if( number >= 1000000 ) 		   { numeral = "million";     number /= 1000000; }
		else if( number >= 1000 ) 			   { numeral = "thousand"; 	  number /= 1000; }
		
		return df.format( number ) + " " + numeral;
	}
}
