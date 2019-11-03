package com.olas.GetRichClicker;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olas.GetRichClicker.user.BusinessModel;
import com.olas.GetRichClicker.user.CoinsModel;
import com.olas.GetRichClicker.user.UpgradesModel;
import com.olas.GetRichClicker.user.UserBusinessModel;
import com.olas.GetRichClicker.user.UserService;

@Controller
public class HomeController 
{
	@Autowired
    private UserService userService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, Principal principal)
	{
		String username = principal.getName();
		
		CoinsModel userCoins = userService.getCoinsByUsername( username );
		double coins = userCoins.getCoins();
		double cps = userCoins.getCps();
		if( coins == (int)coins ){ userCoins.setCoins( (int)coins ); }
		if( cps == (int)cps ){ userCoins.setCps( (int)cps ); }
		model.addAttribute("userCoins", userService.mainCoinsFormatter(userCoins.getCoins()));
		model.addAttribute("userCps", userService.coinsFormatter(userCoins.getCps()));
		
		List<BusinessModel> businessList = userService.getBusiness();
		model.addAttribute("businessList", businessList);
		
		List<UserBusinessModel> userBusinessList = userService.getUserBusiness(username);
		model.addAttribute("userBusinessList", userBusinessList);
		
		List<String> businessPrice =  userService.multiplierAndPriceList(businessList, userBusinessList);
		model.addAttribute("businessPrice", businessPrice);
		
		List<String> businessCps = userService.FormatCps(businessList, userBusinessList);
		model.addAttribute("businessCps", businessCps);
		
		List<UpgradesModel> upgrades = userService.getUpgrades(username);
		model.addAttribute("upgrades", upgrades);
		
		List<String> upgradePrices = userService.getUpgradePrices(upgrades);
		model.addAttribute("upgradePrices", upgradePrices);
		
		return "home";
	}
	
	// ----------------------------------------------------------- AJAX MAPPERS ----------------------------------------------------------- \\
	@RequestMapping(value = "/addCoin")
	public @ResponseBody String makeHimBigger(Principal principal) throws JsonProcessingException
	{
		String username = principal.getName();
		userService.addCoinAfterClick(username);
		CoinsModel userCoins = userService.getCoinsByUsername( username );
		
		List<String> coinsAndCps = new ArrayList<String>();
		coinsAndCps.add( userService.mainCoinsFormatter(userCoins.getCoins()) );
		coinsAndCps.add( userService.coinsFormatter(userCoins.getCps()) );
		
		ObjectMapper mapper = new ObjectMapper();
		String objectToReturn = mapper.writeValueAsString( coinsAndCps );
		return objectToReturn;
	}
	
	@RequestMapping(value = "/buyBusiness")
	public @ResponseBody String buyBusiness(String businessTitle, Principal principal) throws JsonProcessingException
	{
		String username = principal.getName();
		
		double userCoins = userService.getCoinsByUsername(username).getCoins();
		BusinessModel business = userService.getBusinessByTitle(businessTitle);
		UserBusinessModel userBusiness = userService.getUserBusinessByTitle(businessTitle, username);
		if( userCoins >= business.getPrice() * userBusiness.getMultiplier() )
		{
			// buy business
			userService.buyBusiness(businessTitle, username);
			// update cps
			userService.updateCps(businessTitle, username);
			// update coins
			userService.removeCoinsAfterPayment(username, business.getPrice() * userBusiness.getMultiplier());
			// add 0.1 to multiplier
			userService.addToMultiplier(username, businessTitle);
			
			List<UserBusinessModel> userBusinessList = userService.getUserBusiness(username);
			
			ObjectMapper mapper = new ObjectMapper();
			String objectToReturn = mapper.writeValueAsString( userBusinessList );
			return objectToReturn;
		}
		return "NotEnoughCoins";
	}
	
	@RequestMapping(value = "/buyUpgrade")
	public @ResponseBody String buyUpgrade(String upgradeTitle, Principal principal) throws JsonProcessingException
	{
		String username = principal.getName();
		
		if( userService.getUserUpgradeByTitle(upgradeTitle, username) == null )
		{
			double userCoins = userService.getCoinsByUsername(username).getCoins();
			UpgradesModel upgrade = userService.getUpgradeByTitle(upgradeTitle);
			if( userCoins >= upgrade.getPrice() )
			{
				// buy business
				userService.buyUpgrades(upgrade, username);
				// update cps
				userService.updateCpsByUpgrade(upgrade.getTitleID(), upgrade.getMultiplier(), username);
				// update coins
				userService.removeCoinsAfterPayment(username, upgrade.getPrice());
				
				List<String> businessCps = userService.FormatCps(userService.getBusiness(), userService.getUserBusiness(username));
				
				ObjectMapper mapper = new ObjectMapper();
				String objectToReturn = mapper.writeValueAsString( businessCps );
				return objectToReturn;
			}
		}
		return "NotEnoughCoins";
	}
	
	@RequestMapping(value = "/updateUser")
	public @ResponseBody String updateUser(Principal principal) throws JsonProcessingException
	{
		String username = principal.getName();
		
		CoinsModel user = userService.getCoinsByUsername(username);
		List<String> coinsAndCps = new ArrayList<String>();
		coinsAndCps.add( userService.mainCoinsFormatter(user.getCoins()) );
		coinsAndCps.add( userService.coinsFormatter(user.getCps()) );
		
		ObjectMapper mapper = new ObjectMapper();
		String objectToReturn = mapper.writeValueAsString( coinsAndCps );
		return objectToReturn;
	}
	
	@RequestMapping(value = "/updatePrice")
	public @ResponseBody String updatePrice(Principal principal) throws JsonProcessingException
	{
		String username = principal.getName();
		
		List<BusinessModel> business = userService.getBusiness();
		List<UserBusinessModel> userBusinessList = userService.getUserBusiness(username);
		
		ArrayList<String> businessPrice = new ArrayList<String>();
		for(int i=0; i<business.size(); i++)
		{
			businessPrice.add( userService.coinsFormatter(business.get(i).getPrice() * userBusinessList.get(i).getMultiplier()) );
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String objectToReturn = mapper.writeValueAsString( businessPrice );
		return objectToReturn;
	}
	
	@RequestMapping(value = "/canAffordBusiness")
	public @ResponseBody String canAffordBusiness(Principal principal) throws JsonProcessingException
	{
		if( principal != null )
		{
			String username = principal.getName();
			
			double userCoins = userService.getCoinsByUsername(username).getCoins();
			List<BusinessModel> business = userService.getBusiness();
			List<UserBusinessModel> userBusiness = userService.getUserBusiness(username);
			
			List<Boolean> affordableBusiness = new ArrayList<Boolean>();
			for(int i=0; i<business.size(); i++)
			{
				if( userCoins >= business.get(i).getPrice() * userBusiness.get(i).getMultiplier() ){ affordableBusiness.add( true ); }
				else { affordableBusiness.add( false ); }
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String objectToReturn = mapper.writeValueAsString( affordableBusiness );
			return objectToReturn;
		}
		return null;
	}
	
	@RequestMapping(value = "/canAffordUpgrade")
	public @ResponseBody String canAffordUpgrade(Principal principal) throws JsonProcessingException
	{
		if( principal != null )
		{
			String username = principal.getName();
			
			double userCoins = userService.getCoinsByUsername(username).getCoins();
			List<UpgradesModel> upgrades = userService.getUpgrades(username);
			
			List<Boolean> affordableUpgrades = new ArrayList<Boolean>();
			for(int i=0; i<upgrades.size(); i++)
			{
				if( userCoins >= upgrades.get(i).getPrice() ){ affordableUpgrades.add( true ); }
				else { affordableUpgrades.add( false ); }
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String objectToReturn = mapper.writeValueAsString( affordableUpgrades );
			return objectToReturn;
		}
		return null;
	}
	
	@RequestMapping(value = "/updateCoinsPerSecond")
	public @ResponseBody String updateCoinsPerSecond(Principal principal) throws JsonProcessingException
	{
		if( principal != null )
		{
			String username = principal.getName();
			
			CoinsModel user = userService.getCoinsByUsername(username);
			double coins = user.getCoins() + (user.getCps()/10);
			userService.addCoinsPerSecond(username, coins);
			CoinsModel userToReturn = userService.getCoinsByUsername(username);
			
			String result = userService.mainCoinsFormatter( userToReturn.getCoins() );
			
			ObjectMapper mapper = new ObjectMapper();
			String objectToReturn = mapper.writeValueAsString( result );
			
			return objectToReturn;
		}
		
		return null;
	}
	
	@RequestMapping(value = "/startFromScratch")
	public @ResponseBody String startFromScratch(Principal principal) throws JsonProcessingException
	{
		if( principal != null )
		{
			String username = principal.getName();
			
			userService.clearProperties(username);
			
			ObjectMapper mapper = new ObjectMapper();
			String objectToReturn = mapper.writeValueAsString( username );
			
			return objectToReturn;
		}
		
		return null;
	}
}
