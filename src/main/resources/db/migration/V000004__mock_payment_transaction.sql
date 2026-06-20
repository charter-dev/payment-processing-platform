INSERT INTO payment_transaction
(
    trx_id,
    customer_id,
    merchant,
    amount,
    status,
    created_date
)
SELECT
    'TRX-' || LPAD(gs::TEXT, 6, '0'),

    CASE
        WHEN gs % 3 = 0 THEN 'CUST001'
        WHEN gs % 3 = 1 THEN 'CUST002'
        ELSE 'CUST003'
    END,

    CASE
        WHEN gs % 10 = 0 THEN 'ALPHA_STORE'
        WHEN gs % 9 = 0 THEN 'BETA_MART'
        WHEN gs % 8 = 0 THEN 'GAMMA_PAY'
        WHEN gs % 7 = 0 THEN 'DELTA_SHOP'
        WHEN gs % 6 = 0 THEN 'OMEGA_MARKET'
        WHEN gs % 5 = 0 THEN 'NOVA_RETAIL'
        WHEN gs % 4 = 0 THEN 'ORION_STORE'
        WHEN gs % 3 = 0 THEN 'ATLAS_COMMERCE'
        WHEN gs % 2 = 0 THEN 'ZENITH_PAY'
        ELSE 'PIONEER_MART'
    END,

    ROUND((RANDOM() * 5000000 + 10000)::NUMERIC, 2),

    CASE
        WHEN gs % 10 < 8 THEN 'SUCCESS'
        WHEN gs % 10 = 8 THEN 'PENDING'
        ELSE 'FAILED'
    END,

    NOW() - ((gs % 30) * INTERVAL '1 DAY')

FROM generate_series(1,1000) gs;