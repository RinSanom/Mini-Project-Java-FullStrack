-- Create the database schema for the E-commerce application

-- Create users table (if not exists)
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT true
);

-- Create products table (if not exists)
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    p_name VARCHAR(200) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    qty INTEGER NOT NULL DEFAULT 0,
    is_deleted BOOLEAN DEFAULT false,
    p_uuid VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Update orders_products table to include new columns
-- First check if the table exists, if not create it
CREATE TABLE IF NOT EXISTS orders_products (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    p_id INTEGER NOT NULL,
    quantity INTEGER DEFAULT 1,
    total_amount DECIMAL(10,2) DEFAULT 0.00,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    order_status VARCHAR(50) DEFAULT 'COMPLETED',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (p_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Add new columns to existing orders_products table if they don't exist
DO $$ 
BEGIN
    -- Add quantity column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'orders_products' AND column_name = 'quantity') THEN
        ALTER TABLE orders_products ADD COLUMN quantity INTEGER DEFAULT 1;
    END IF;
    
    -- Add total_amount column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'orders_products' AND column_name = 'total_amount') THEN
        ALTER TABLE orders_products ADD COLUMN total_amount DECIMAL(10,2) DEFAULT 0.00;
    END IF;
    
    -- Add order_date column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'orders_products' AND column_name = 'order_date') THEN
        ALTER TABLE orders_products ADD COLUMN order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
    END IF;
    
    -- Add order_status column if it doesn't exist
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'orders_products' AND column_name = 'order_status') THEN
        ALTER TABLE orders_products ADD COLUMN order_status VARCHAR(50) DEFAULT 'COMPLETED';
    END IF;
    
    -- Add id column if it doesn't exist (for primary key)
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name = 'orders_products' AND column_name = 'id') THEN
        ALTER TABLE orders_products ADD COLUMN id SERIAL PRIMARY KEY;
    END IF;
END $$;

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_orders_products_user_id ON orders_products(user_id);
CREATE INDEX IF NOT EXISTS idx_orders_products_p_id ON orders_products(p_id);
CREATE INDEX IF NOT EXISTS idx_orders_products_order_date ON orders_products(order_date);
CREATE INDEX IF NOT EXISTS idx_products_uuid ON products(p_uuid);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- Insert some sample data if tables are empty
INSERT INTO users (username, email, password_hash) 
SELECT 'admin', 'admin@example.com', '$2a$10$example_hash_here'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@example.com');

-- Sample products
INSERT INTO products (p_name, price, qty, p_uuid) 
SELECT 'Sample Product 1', 29.99, 100, 'uuid-sample-1'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE p_uuid = 'uuid-sample-1');

INSERT INTO products (p_name, price, qty, p_uuid) 
SELECT 'Sample Product 2', 49.99, 50, 'uuid-sample-2'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE p_uuid = 'uuid-sample-2');

INSERT INTO products (p_name, price, qty, p_uuid) 
SELECT 'Sample Product 3', 19.99, 200, 'uuid-sample-3'
WHERE NOT EXISTS (SELECT 1 FROM products WHERE p_uuid = 'uuid-sample-3');
