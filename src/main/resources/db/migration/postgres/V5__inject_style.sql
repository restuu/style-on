insert into public.style (id, name, image_url, key_style, movement, strap_material, colors, price, note)
values ( 31, 'The Deep Diver', '/images/the-deep-diver.png', '{DIVER}', 'AUTOMATIC', 'STAINLESS_STEEL', '{BLACK,BLUE}'
	   , 'MID_LEVEL', 'Utamakan bezel keramik dan daya tahan air 200m+.')
	 , ( 32, 'The Classic Businessman', '/images/the-classic-businessman.png', '{DRESS}', 'AUTOMATIC', 'LEATHER'
	   , '{SILVER,WHITE}', 'MID_LEVEL', 'Harus tipis agar pas di bawah manset kemeja.')
	 , ( 33, 'The Weekend Hiker', '/images/the-weekend-hiker.png', '{FIELD}', 'SOLAR_POWERED', 'CANVAS', '{GREEN,BEIGE}'
	   , 'ENTRY_LEVEL', 'Cari dial yang sangat mudah dibaca (legibility).')
	 , ( 34, 'The Racing Enthusiast', '/images/the-racing-enthusiast.png', '{SPORT}', 'QUARTZ_BATTERY', 'RUBBER'
	   , '{RED,BLACK}', 'MID_LEVEL', 'Chronograph harus mudah diakses dan dial berkarakter.')
	 , ( 35, 'The Desk Pilot', '/images/the-desk-pilot.png', '{PILOT}', 'MANUAL_WINDING', 'LEATHER', '{BROWN,BLACK}'
	   , 'MID_LEVEL', 'Perhatikan ukuran crown dan rivet pada strap kulit.')
	 , ( 36, 'The Ultra-Luxury Gala', '/images/the-ultra-luxury-gala.png', '{DRESS}', 'AUTOMATIC', 'GOLD_METAL'
	   , '{GOLD,SILVER}', 'LUXURY', 'Carilah sentuhan sunburst dial dan material emas padat.')
	 , ( 37, 'The Minimalist', '/images/the-minimalist.png', '{DRESS}', 'QUARTZ_BATTERY', 'LEATHER', '{WHITE,BLACK}'
	   , 'ENTRY_LEVEL', 'Less is more, pilih hands dan marker yang sangat tipis.')
	 , ( 38, 'The Extreme Sport', '/images/the-extreme-sport.png', '{SPORT}', 'QUARTZ_BATTERY', 'STAINLESS_STEEL'
	   , '{BLACK,SILVER}', 'MID_LEVEL', 'Pilih ketahanan banting dan fitur stopwatch akurat.')
	 , ( 39, 'The Grand Complication', '/images/the-grand-complication.png', '{DRESS,PILOT}', 'MECHANICAL'
	   , 'CROCODILE_LEATHER', '{SILVER,BLACK}', 'PREMIUM'
	   , 'Pamerkan kerumitan mesin melalui open heart atau display back.')
	 , ( 40, 'The Vintage Chrono', '/images/the-vintage-chrono.png', '{PILOT,SPORT}', 'MECHANICAL', 'PATINA_LEATHER'
	   , '{BROWN,BEIGE}', 'MID_LEVEL', 'Lume bernuansa tua (patina) akan menambah nilai otentik.')
	 , ( 41, 'The Elegant Slim', '/images/the-elegant-slim.png', '{DRESS}', 'AUTOMATIC', 'STAINLESS_STEEL', '{GOLD}'
	   , 'MID_LEVEL', 'Warna Rose Gold paling cocok dipadukan dengan strap cokelat muda.')
	 , ( 42, 'The Iconic Heritage', '/images/the-iconic-heritage.png', '{CASUAL,DIVER}', 'AUTOMATIC', 'STAINLESS_STEEL'
	   , '{BLACK,SILVER}', 'PREMIUM', 'Jam dengan sejarah brand yang panjang menjadi statement status.')
	 , ( 43, 'The Smart Casual', '/images/the-smart-casual.png', '{CASUAL}', 'QUARTZ_BATTERY', 'STAINLESS_STEEL'
	   , '{BLUE}', 'ENTRY_LEVEL', 'Perpaduan bracelet metal dan dial warna cerah (sunburst).')
	 , ( 44, 'The Traditionalist', '/images/the-traditionalist.png', '{DRESS}', 'MANUAL_WINDING', 'LEATHER', '{GOLD}'
	   , 'PREMIUM', 'Hindari komplikasi berlebihan; fokus pada keindahan desain.')
	 , ( 45, 'The Tech Avant-Garde', '/images/the-tech-avant-garde.png', '{SPORT,CASUAL}', 'SOLAR_POWERED'
	   , 'TITANIUM_RUBBER', '{GRAY,GRAY}', 'PREMIUM'
	   , 'Cari model hybrid atau rugged dengan teknologi GPS terintegrasi.')
on conflict (id) do nothing;