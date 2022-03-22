
CREATE TABLE "image" (
     "id" SERIAL PRIMARY KEY,
     "image" bytea,
     "main" boolean,
     "description" varchar,
     "product_id" serial
);

ALTER TABLE "image" ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");