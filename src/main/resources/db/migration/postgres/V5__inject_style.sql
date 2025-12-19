insert into public.style (id, name, image_url, key_style, movement, strap_material, colors, price, note)
values ( 31, 'The Deep Diver', '/images/styles/1.jpg', '{DIVER}', 'AUTOMATIC', 'STAINLESS_STEEL', '{BLACK,BLUE}'
	   , 'MID_LEVEL', 'Utamakan bezel keramik dan daya tahan air 200m+.')
	 , ( 32, 'The Classic Businessman', '/images/styles/2.jpg', '{DRESS}', 'AUTOMATIC', 'LEATHER'
	   , '{SILVER,WHITE}', 'MID_LEVEL', 'Harus tipis agar pas di bawah manset kemeja.')
	 , ( 33, 'The Weekend Hiker', '/images/styles/3.jpg', '{FIELD}', 'SOLAR_POWERED', 'CANVAS', '{GREEN,BEIGE}'
	   , 'ENTRY_LEVEL', 'Cari dial yang sangat mudah dibaca (legibility).')
	 , ( 34, 'The Racing Enthusiast', '/images/styles/4.jpg', '{SPORT}', 'QUARTZ_BATTERY', 'RUBBER'
	   , '{RED,BLACK}', 'MID_LEVEL', 'Chronograph harus mudah diakses dan dial berkarakter.')
	 , ( 35, 'The Desk Pilot', '/images/styles/5.jpg', '{PILOT}', 'MANUAL_WINDING', 'LEATHER', '{BROWN,BLACK}'
	   , 'MID_LEVEL', 'Perhatikan ukuran crown dan rivet pada strap kulit.')
	 , ( 36, 'The Ultra-Luxury Gala', '/images/styles/6.jpg', '{DRESS}', 'AUTOMATIC', 'GOLD_METAL'
	   , '{GOLD,SILVER}', 'LUXURY', 'Carilah sentuhan sunburst dial dan material emas padat.')
	 , ( 37, 'The Minimalist', '/images/styles/7.jpg', '{DRESS}', 'QUARTZ_BATTERY', 'LEATHER', '{WHITE,BLACK}'
	   , 'ENTRY_LEVEL', 'Less is more, pilih hands dan marker yang sangat tipis.')
	 , ( 38, 'The Extreme Sport', '/images/styles/8.jpg', '{SPORT}', 'QUARTZ_BATTERY', 'STAINLESS_STEEL'
	   , '{BLACK,SILVER}', 'MID_LEVEL', 'Pilih ketahanan banting dan fitur stopwatch akurat.')
	 , ( 39, 'The Grand Complication', '/images/styles/9.jpg', '{DRESS,PILOT}', 'MECHANICAL'
	   , 'CROCODILE_LEATHER', '{SILVER,BLACK}', 'PREMIUM'
	   , 'Pamerkan kerumitan mesin melalui open heart atau display back.')
	 , ( 40, 'The Vintage Chrono', '/images/styles/10.jpg', '{PILOT,SPORT}', 'MECHANICAL', 'PATINA_LEATHER'
	   , '{BROWN,BEIGE}', 'MID_LEVEL', 'Lume bernuansa tua (patina) akan menambah nilai otentik.')
	 , ( 41, 'The Elegant Slim', '/images/styles/11.jpg', '{DRESS}', 'AUTOMATIC', 'STAINLESS_STEEL', '{GOLD}'
	   , 'MID_LEVEL', 'Warna Rose Gold paling cocok dipadukan dengan strap cokelat muda.')
	 , ( 42, 'The Iconic Heritage', '/images/styles/12.jpg', '{CASUAL,DIVER}', 'AUTOMATIC', 'STAINLESS_STEEL'
	   , '{BLACK,SILVER}', 'PREMIUM', 'Jam dengan sejarah brand yang panjang menjadi statement status.')
	 , ( 43, 'The Smart Casual', '/images/styles/13.jpg', '{CASUAL}', 'QUARTZ_BATTERY', 'STAINLESS_STEEL'
	   , '{BLUE}', 'ENTRY_LEVEL', 'Perpaduan bracelet metal dan dial warna cerah (sunburst).')
	 , ( 44, 'The Traditionalist', '/images/styles/14.jpg', '{DRESS}', 'MANUAL_WINDING', 'LEATHER', '{GOLD}'
	   , 'PREMIUM', 'Hindari komplikasi berlebihan; fokus pada keindahan desain.')
	 , ( 45, 'The Tech Avant-Garde', '/images/styles/15.jpg', '{SPORT,CASUAL}', 'SOLAR_POWERED'
	   , 'TITANIUM_RUBBER', '{GRAY,GRAY}', 'PREMIUM'
	   , 'Cari model hybrid atau rugged dengan teknologi GPS terintegrasi.')
on conflict (id) do update
	set name           = excluded.name
	  , image_url      = excluded.image_url
	  , key_style      = excluded.key_style
	  , movement       = excluded.movement
	  , strap_material = excluded.strap_material
	  , colors         = excluded.colors
	  , price          = excluded.price
	  , note           = excluded.note;