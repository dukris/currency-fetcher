INSERT INTO fetcher.rates (id, currency, rate, date)
VALUES (gen_random_uuid(), 'USDT', 1, '2024-02-28 12:00:00')
ON CONFLICT DO NOTHING;