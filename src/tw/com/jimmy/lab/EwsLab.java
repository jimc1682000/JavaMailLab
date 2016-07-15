package tw.com.jimmy.lab;

import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;

public class EwsLab {
	public static void main(String[] args) {
		ExchangeService service = new ExchangeService(
				ExchangeVersion.Exchange2010);
		ExchangeCredentials credentials = new WebCredentials(
				"myaccount@my.domain", "myPassword", "my.domain");
		service.setCredentials(credentials);
		try {
			service.autodiscoverUrl("myaccount@my.domain",
					new RedirectionUrlCallback());
			EmailMessage msg = new EmailMessage(service);
			msg.setSubject("Hello EWS!");
			msg.setBody(MessageBody
					.getMessageBodyFromText("Sent using the EWS Java API."));
			msg.getToRecipients().add("myaccount2@my.domain");
			msg.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class RedirectionUrlCallback implements
			IAutodiscoverRedirectionUrl {
		public boolean autodiscoverRedirectionUrlValidationCallback(
				String redirectionUrl) {
			return redirectionUrl.toLowerCase().startsWith("https://");
		}
	}
}
