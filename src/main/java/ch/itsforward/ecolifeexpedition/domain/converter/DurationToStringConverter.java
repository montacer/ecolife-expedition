package ch.itsforward.ecolifeexpedition.domain.converter;

import java.time.Duration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class DurationToStringConverter implements AttributeConverter<Duration, String>
{

    @Override
    public String convertToDatabaseColumn(Duration duration)
    {
        return duration == null ? null : duration.toString();
    }

    @Override
    public Duration convertToEntityAttribute(String dbData)
    {
        return dbData == null ? null : Duration.parse(dbData);
    }
}