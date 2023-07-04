package com.takeoff.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.takeoff.model.Data;
import com.takeoff.model.OIData;
import com.takeoff.model.ZData;
import com.takeoff.repository.FnoStockRepository;
import com.takeoff.repository.TokenRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "MYTRADE")
public class MyTradingSetupController {

	@Autowired
	FnoStockRepository repository;

	@RequestMapping(value = "demo")
	public String demo() {
		return "demo";

	}

	@RequestMapping(value = "getFnoList")
	public List<String> getFnoList() {

		return repository.findAll().stream().map(o -> {
			return o.getItoken() + "#" + o.getSymbol() + "#" + getLastPrice(o.getItoken() + "");
		}).collect(Collectors.toList());

	}

	static RestTemplate template = new RestTemplate();
	static HttpHeaders headers = new HttpHeaders();
	static HttpEntity<String> entity = null;
	static List<ZData> zdata = null;
	static LocalDate fromDate = LocalDate.now().minusDays(5);
	static LocalDate toDate = LocalDate.now().plusDays(5);

	@Autowired
	public MyTradingSetupController(TokenRepository trepository) {
		String token = "+YCesV7aoyEk754ReZNEFzGUQNeWeOOqdo9QwZlWfjZ8uOGu0rKWG3Hrm5ZluhLGRgQE5NmH0RtQtqHtiW3w4pTGOFptl1WRVFzfkgLAwmMIpFxkdbwKSA==";
		System.out.println("Token is :: " + token);
		headers.set("Authorization", "enctoken " + token);
		headers.set("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		entity = new HttpEntity<String>(headers);
		zdata = getInstruments();
	}

	private List<ZData> getInstruments() {

		String output = template.exchange("https://api.kite.trade/instruments", HttpMethod.GET, entity, String.class)
				.getBody();
		List<ZData> zdata = new ArrayList<>();
		String[] data = output.split("\n");
		for (int i = 1; i < data.length; i++)

			zdata.add(new ZData(data[i]));

		return zdata;
	}

	@RequestMapping(value = "getData")
	public List<OIData> getData(@RequestParam("instrument") String instrument) {
		String call[] = { "CE", "PE" };
		String name = repository.findById(Long.valueOf(instrument)).get().getName();
		System.out.println(name);
		Double lastPrice = getLastPrice(instrument);
		System.out.println(instrument);
		System.out.println(lastPrice);
		List<String> mapids = getCEPE(lastPrice, name);
		System.out.println(mapids);
		List<OIData> OIDataList = new ArrayList<>();

		for (int j = 0; j < mapids.size(); j++) {

			String spiltData[] = mapids.get(j).split(",");

			for (int k = 0; k < 2; k++) {

				OIData oiData = new OIData();
				oiData.setPrice(Double.parseDouble(spiltData[0]));
				oiData.setInstrument(Long.valueOf(spiltData[1 + k]));
				oiData.setCall(call[k]);

				String output = template.exchange(
						"https://kite.zerodha.com/oms/instruments/historical/" + oiData.getInstrument()
								+ "/minute?user_id=IO7052&oi=1&from=" + fromDate + "&to=" + toDate,
						HttpMethod.GET, entity, String.class).getBody();

				// //System.out.println(output);

				int index = output.indexOf("candles");
				int index1 = output.indexOf("[[", index);
				String dataStr = output.toString().substring(index1 + 2).replace("],[", "\n").replace("]]}}", "");
				String candles[] = dataStr.split("\n");

				int startFrom = candles.length - 500;
				if (startFrom < 0)
					startFrom = 0;

				List<Data> data = new ArrayList<>();

				for (int i = startFrom; i < candles.length; i++) {

					String date1;
					Double open1, high1, close1, low1, volume1, oi1;
					date1 = candles[i].split(",")[0];
					open1 = Double.parseDouble(candles[i].split(",")[1]);
					high1 = Double.parseDouble(candles[i].split(",")[2]);
					low1 = Double.parseDouble(candles[i].split(",")[3]);
					close1 = Double.parseDouble(candles[i].split(",")[4]);
					volume1 = Double.parseDouble(candles[i].split(",")[5]);
					oi1 = Double.parseDouble(candles[i].split(",")[6]);
					data.add(new Data(date1, open1, high1, low1, close1, volume1, oi1));
				}

				oiData.setClose(data.stream().map(o -> o.getClose()).collect(Collectors.toList()));
				oiData.setOpen(data.stream().map(o -> o.getOpen()).collect(Collectors.toList()));
				oiData.setHigh(data.stream().map(o -> o.getHigh()).collect(Collectors.toList()));
				oiData.setLow(data.stream().map(o -> o.getLow()).collect(Collectors.toList()));
				oiData.setOi(data.stream().map(o -> o.getOi() / 50).collect(Collectors.toList()));
				oiData.setVol(data.stream().map(o -> o.getVolume()).collect(Collectors.toList()));
				oiData.setDate(data.stream().map(o -> o.getDate().replaceAll("\"", "").substring(11, 19))
						.collect(Collectors.toList()));

				OIDataList.add(oiData);
			}

		}
		return OIDataList;
	}

	@RequestMapping(value = "getLastPrice")
	public Double getLastPrice(@RequestParam("instrument") String instrument) {

		String output = template.exchange(
				"https://kite.zerodha.com/oms/instruments/historical/" + instrument
						+ "/minute?user_id=IO7052&oi=1&from=" + fromDate + "&to=" + toDate,
				HttpMethod.GET, entity, String.class).getBody();

		int index = output.indexOf("candles");
		int index1 = output.indexOf("[[", index);
		String dataStr = output.toString().substring(index1 + 2).replace("],[", "\n").replace("]]}}", "");
		String candles[] = dataStr.split("\n");
		return Double.parseDouble(candles[candles.length - 1].split(",")[4]);
	}

	@RequestMapping(value = "getCEPE")
	public List<String> getCEPE(@RequestParam("lastPrice") Double lastPrice, @RequestParam("name") String name) {

		List<String> data = new ArrayList<>();
		String expiryDate = zdata.stream()
				.filter(o -> o.getName().equals("\"" + name + "\"") && o.getItype().equals("CE"))
				.map(o -> o.getExpiry()).sorted().limit(1).collect(Collectors.toList()).get(0);

		System.out.println(name + " " + expiryDate);
		List<Double> prices = zdata.stream()
				.filter(o -> o.getName().equals("\"" + name + "\"") && o.getExpiry().equals(expiryDate)
						&& o.getStrike().compareTo(lastPrice) >= 0)
				.map(o -> o.getStrike()).limit(7).collect(Collectors.toList());
		prices.addAll(zdata.stream()
				.filter(o -> o.getName().equals("\"" + name + "\"") && o.getExpiry().equals(expiryDate)
						&& o.getStrike().compareTo(lastPrice) <= 0)
				.sorted((o1, o2) -> o2.getStrike().compareTo(o1.getStrike())).limit(7).map(o -> o.getStrike())
				.collect(Collectors.toList()));
		prices = prices.stream().distinct().sorted().collect(Collectors.toList());

		for (int i = 0; i < prices.size() && i < 7; i++) {
			String pricestr = prices.get(i) + "";
			// System.out.println(pricestr);
			String CE = (zdata.stream()
					// .peek(o->System.out.println(o.getStrike().toString() + " "+ (pricestr) + "
					// "+o.getStrike().toString().equals(pricestr)))
					.filter(o -> o.getName().equals("\"" + name + "\"") && o.getExpiry().equals(expiryDate)
							&& o.getStrike().toString().equals(pricestr) && o.getItype().equals("CE"))
					.collect(Collectors.toList()).get(0)).getItoken() + "";
			String PE = (zdata.stream()
					.filter(o -> o.getName().equals("\"" + name + "\"") && o.getExpiry().equals(expiryDate)
							&& o.getStrike().toString().equals(pricestr) && o.getItype().equals("PE"))
					.collect(Collectors.toList()).get(0)).getItoken() + "";

			data.add(pricestr + "," + CE + "," + PE);
		}
		return data;
	}

}
