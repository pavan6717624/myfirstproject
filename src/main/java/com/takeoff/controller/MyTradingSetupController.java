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

	static RestTemplate template = new RestTemplate();
	static HttpHeaders headers = new HttpHeaders();
	static HttpEntity<String> entity = null;

	{
		headers.set("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		headers.set("Referer", "https://www.samco.in/bhavcopy-nse-bse-mcx");

		entity = new HttpEntity<String>(headers);

	}

	List<Fno> futures = new ArrayList<>();
	List<Stock> Delivery = new ArrayList<>();
	
	@RequestMapping(value = "getLastPrice")
	public List<Data> getLastPrice() throws Exception {
		
		List<Fno> futures = new ArrayList<>();
		List<MapData> chartData = new ArrayList<>();
		URL url = new URL("https://www.samco.in/");
		URLConnection con = url.openConnection();

		String setCookie = "";

		for (int i = 0;; i++) {
			String headerName = con.getHeaderFieldKey(i);
			String headerValue = con.getHeaderField(i);

			if (null != headerName && headerName.equals("Set-Cookie"))
				setCookie = headerValue;

			if (headerName == null && headerValue == null) {
				break;
			}

		}

		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		localDate = localDate.minusDays(1);
		for (int d = 0; d < 30; d++) {

			// System.out.println(localDate.format(formatter1));

			String urlParameters = "start_date=" + localDate.format(formatter1) + "&end_date="
					+ localDate.format(formatter1)
					+ "&show_or_down=1&bhavcopy_data%5B%5D=NSE&bhavcopy_data%5B%5D=NSEFO";
			url = new URL("https://www.samco.in/bse_nse_mcx/getBhavcopy");
			con = url.openConnection();

			System.out.println(urlParameters);

			con.setDoOutput(true);

			OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());

			writer.write(urlParameters);
			writer.flush();
			String line1;
			String data = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

			while ((line1 = reader.readLine()) != null) {
				Boolean loop = false;
				int index1 = 0;
				do {
					index1 = line1.indexOf("https://www.samco.in/bse_nse_mcx/datacopy/", index1);
					if (index1 != -1) {
						data += line1.substring(index1, line1.indexOf("\"", index1)) + " - ";
						index1 = line1.indexOf("\"", index1);
						loop = true;
					} else
						loop = false;
				} while (loop);

			}
			 System.out.println(data);
			writer.close();
			reader.close();

			String urls[] = data.split(" - ");

			try {
				url = new URL(urls[0].trim());

				con = url.openConnection();
			} catch (Exception ex) {
				System.out.println(ex);
				localDate = localDate.minusDays(1);
				continue;
			}
			con.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 6.0; pl; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2");
			con.addRequestProperty("Referer", "https://www.samco.in/bhavcopy-nse-bse-mcx");
			con.setRequestProperty("Cookie", setCookie);
			con.setRequestProperty("Expires", " Thu, 19 Nov 1981 08:52:00 GMT");
			con.setRequestProperty("Cache-Control", "no-store, no-cache, must-revalidate");
			con.setRequestProperty("Pragma", "no-cache");
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = "";
			List<Stock> stocks = new ArrayList<>();
			List<Fno> fnos = new ArrayList<>();
			
			while ((line = br.readLine()) != null)
				if (line.indexOf(",EQ,") != -1)
					stocks.add(new Stock(line));

			url = new URL(urls[1].trim());

			con = url.openConnection();
			con.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 6.0; pl; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2");
			con.addRequestProperty("Referer", "https://www.samco.in/bhavcopy-nse-bse-mcx");
			con.setRequestProperty("Cookie", setCookie);
			con.setRequestProperty("Expires", " Thu, 19 Nov 1981 08:52:00 GMT");
			con.setRequestProperty("Cache-Control", "no-store, no-cache, must-revalidate");
			con.setRequestProperty("Pragma", "no-cache");
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));

			line = "";

			DateTimeFormatter formatter = new DateTimeFormatterBuilder()
					// case insensitive to parse JAN and FEB
					.parseCaseInsensitive()
					// add pattern
					.appendPattern("dd-MMM-yyyy")
					// create formatter (use English Locale to parse month names)
					.toFormatter(Locale.ENGLISH);
			while ((line = br.readLine()) != null) {
				String cols[] = line.split(",");

				if (cols[0].equals("OPTSTK")
						&& ChronoUnit.DAYS.between(getExpiryDate(LocalDate.parse(cols[cols.length - 1].trim(), formatter)),
								LocalDate.parse(cols[2].trim(), formatter)) < 6)
				{
//					System.out.println(line+" "+LocalDate.parse(cols[cols.length - 1].trim(), formatter) +" "+
//							LocalDate.parse(cols[2].trim(), formatter) +" "+ChronoUnit.DAYS.between(LocalDate.parse(cols[cols.length - 1].trim(), formatter),
//									LocalDate.parse(cols[2].trim(), formatter)));
					fnos.add(new Fno(line));
				}
				
				if (cols[0].equals("FUTSTK")
						&& ChronoUnit.DAYS.between(getExpiryDate(LocalDate.parse(cols[cols.length - 1].trim(), formatter)),
								LocalDate.parse(cols[2].trim(), formatter)) < 6)
				{
//					System.out.println(line+" "+LocalDate.parse(cols[cols.length - 1].trim(), formatter) +" "+
//							LocalDate.parse(cols[2].trim(), formatter) +" "+ChronoUnit.DAYS.between(LocalDate.parse(cols[cols.length - 1].trim(), formatter),
//									LocalDate.parse(cols[2].trim(), formatter)));
					futures.add(new Fno(line));
				}
			}

			this.futures=futures;
			
			Map<String, List<Fno>> gdata = fnos.stream()
					.collect(Collectors.groupingBy(o -> o.getSymbol() + "-" + o.getOption_typ()));

			List<String> keys = gdata.keySet().stream().sorted().collect(Collectors.toList());
			
		

			for (int i = 0; i < keys.size(); i++) {
				MapData value = gdata.get(keys.get(i)).stream()
						.sorted(Comparator.comparingDouble(Fno::getOpen_int).reversed()).limit(1)
						.map(o -> new MapData(o,
								stocks.stream().filter(o1 -> o1.getSymbol().equals(o.getSymbol()))
										.collect(Collectors.toList()).get(0).getClose()))
						
						.collect(Collectors.toList()).get(0);

				chartData.add(value);

			}
			localDate = localDate.minusDays(1);
		}
		
		Map<String, List<MapData>> mdata=chartData.stream().collect(Collectors.groupingBy(o -> o.getSymbol()));
		
		List<String> mkeys=mdata.keySet().stream().collect(Collectors.toList());
		
		List<Data> sdata=new ArrayList<>();
		
		for(int i=0;i<mkeys.size();i++)
		{
			Data data=new Data(mdata.get(mkeys.get(i)));
			sdata.add(data);
		}

		

		return sdata.stream().sorted(Comparator.comparing(Data::getName).reversed()).collect(Collectors.toList());
	}
	
	public LocalDate getExpiryDate(LocalDate date)
	{
		LocalDate lastThrusday = date.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
	
		if(lastThrusday.compareTo(date) < 0)
			lastThrusday = date.plusDays(20).with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
		//System.out.println(date+" "+lastThrusday);
		return lastThrusday;
	}
	
	@RequestMapping(value = "getFutureOIs")
	public Collection<List<Futures>> getFutureOIs() throws Exception {
		
		
	
		return (futures.stream().sorted(Comparator.comparing(Fno::getTimestamp)).map(o->new Futures(o)).collect(Collectors.groupingBy(o -> o.getSymbol()))).values();
		
	}
	
	@RequestMapping(value = "getDelivery")
	public void getDelivery() throws Exception {
		
		URL url=new URL("https://nsearchives.nseindia.com/archives/equities/mto/MTO_31072023.DAT");
		getFinalURL(url);
	
	}
	public URL getFinalURL(URL url) throws Exception {
		
		URLConnection con = url.openConnection();
		System.out.println( "orignal url: " + con.getURL() );
		con.connect();
		System.out.println( "connected url: " + con.getURL() );
		InputStream is = con.getInputStream();
		System.out.println( "redirected url: " + con.getURL() );
		is.close();
		
	 return url;
		
	}

	/*@Autowired
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
		String token = "YuK4PBzmZnkgiuvF39gaJdeAqregLxFbJ4sJMJTiHPWA6AYCF4RmkRvk5DE+1ThlX4w8SLG3+lfqUA2LHo+xco3SztzkK6Pf7w+LTo8iegg1ITC6X4JFHA==";
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
*/
}
