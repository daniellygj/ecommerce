CREATE TABLE "filter" (
    "id" SERIAL PRIMARY KEY,
    "name" varchar(100),
    "created_at" timestamp,
    "modified_at" timestamp,
    "deleted_at" timestamp
);

CREATE TABLE "sub_filter" (
    "id" SERIAL PRIMARY KEY,
    "filter_id" serial,
    "name" varchar(100),
    "created_at" timestamp,
    "modified_at" timestamp,
    "deleted_at" timestamp
);

CREATE TABLE "product_filter" (
    "id" SERIAL PRIMARY KEY,
    "product_id" serial,
    "filter_id" serial
);

ALTER TABLE "product_filter" ADD FOREIGN KEY ("filter_id") REFERENCES "filter" ("id");
ALTER TABLE "product_filter" ADD FOREIGN KEY ("product_id") REFERENCES "product" ("id");
ALTER TABLE "sub_filter" ADD FOREIGN KEY ("filter_id") REFERENCES "filter" ("id");