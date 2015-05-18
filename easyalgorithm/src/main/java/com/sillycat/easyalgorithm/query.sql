SELECT  
    e1.sensor_id,
    e1.event_type,
    e1.value
FROM 
    events e1 inner JOIN 
    (SELECT 
        sensor_id, 
        event_type, 
        max(time) AS max_time 
    FROM 
        events 
    GROUP BY 
        sensor_id, event_type) e2
    ON 
    e1.sensor_id=e2.sensor_id AND 
    e1.event_type = e2.event_type AND
    e1.time=e2.max_time;