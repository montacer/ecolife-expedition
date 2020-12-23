package ch.itsforward.ecolifeexpedition.config;

import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

@Configuration
@Profile({ JHipsterConstants.SPRING_PROFILE_PRODUCTION })
public class StaticResourcesWebConfiguration implements WebMvcConfigurer {

	@Value("${ecolife.mediaStorageLocation.video}")
	protected String video_media_resource ; 
	public static String RESOURCE_VIDEO_MEDIA_LOCATIONS;
	
	@Value("${ecolife.mediaStorageLocation.audio}")
	protected String audio_media_resource ;
	public static String RESOURCE_AUDIO_MEDIA_LOCATIONS;

	protected static final String[] RESOURCE_LOCATIONS = new String[] { "classpath:/static/app/",
			"classpath:/static/content/", "classpath:/static/i18n/" };
	protected static final String[] RESOURCE_PATHS = new String[] { "/app/*", "/content/*", "/i18n/*" };

	private final JHipsterProperties jhipsterProperties;

	@PostConstruct
	public void initExternalResource() {
		RESOURCE_VIDEO_MEDIA_LOCATIONS=video_media_resource;
		RESOURCE_AUDIO_MEDIA_LOCATIONS=audio_media_resource;
	}

	public StaticResourcesWebConfiguration(JHipsterProperties jHipsterProperties) {
		this.jhipsterProperties = jHipsterProperties;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		ResourceHandlerRegistration resourceHandlerRegistration = appendResourceHandler(registry);
		initializeResourceHandler(resourceHandlerRegistration);
	}

	protected ResourceHandlerRegistration appendResourceHandler(ResourceHandlerRegistry registry) {
		return registry.addResourceHandler(RESOURCE_PATHS);
	}

	protected void initializeResourceHandler(ResourceHandlerRegistration resourceHandlerRegistration) {
		resourceHandlerRegistration.addResourceLocations(RESOURCE_LOCATIONS).setCacheControl(getCacheControl());
	}

	protected CacheControl getCacheControl() {
		return CacheControl.maxAge(getJHipsterHttpCacheProperty(), TimeUnit.DAYS).cachePublic();
	}

	private int getJHipsterHttpCacheProperty() {
		return jhipsterProperties.getHttp().getCache().getTimeToLiveInDays();
	}

}
