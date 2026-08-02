CREATE TABLE IF NOT EXISTS "tenants" (
    "id" BIGSERIAL NOT NULL PRIMARY KEY UNIQUE,
    "name" VARCHAR(255) NOT NULL,
    "slug" VARCHAR(255) NOT NULL UNIQUE,
    "country" VARCHAR(50) NOT NULL,
    "is_active" BOOLEAN NOT NULL DEFAULT true,
    "created_at" TIMESTAMP NOT NULL default current_timestamp,
    "updated_at" TIMESTAMP NOT NULL,
    "created_by" VARCHAR(100) NOT NULL,
    "updated_by" VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS "modules" (
   "id" BIGSERIAL NOT NULL PRIMARY KEY ,
   "name" VARCHAR(150) NOT NULL UNIQUE,
   "module_key" VARCHAR(255) NOT NULL UNIQUE,
   "created_at" TIMESTAMP NOT NULL default current_timestamp,
   "updated_at" TIMESTAMP NOT NULL,
   "created_by" VARCHAR(100) NOT NULL,
   "updated_by" VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS "module_subscriptions" (
    "id" BIGSERIAL NOT NULL PRIMARY KEY,
    "tenant_id" BIGINT NOT NULL,
    "module_id" BIGINT NOT NULL,
    "is_enabled" BOOLEAN NOT NULL DEFAULT true,
    "plan" VARCHAR(100) NOT NULL,
    "activated_at" TIMESTAMP NOT NULL,
    "expires_at" TIMESTAMP NOT NULL,
    "created_at" TIMESTAMP NOT NULL default current_timestamp,
    "updated_at" TIMESTAMP NOT NULL,
    "created_by" VARCHAR(100) NOT NULL,
    "updated_by" VARCHAR(100) NOT NULL,
    UNIQUE("tenant_id", "module_id")
);

ALTER TABLE "module_subscriptions"
    ADD CONSTRAINT fk_modulesubscription_module
        FOREIGN KEY ("module_id") REFERENCES "modules"("id")
            ON DELETE CASCADE;

ALTER TABLE "module_subscriptions"
    ADD CONSTRAINT fk_modulesubscription_tenant
        FOREIGN KEY ("tenant_id") REFERENCES "tenants"("id")
            ON DELETE CASCADE;


CREATE TABLE IF NOT EXISTS "users" (
    "id" BIGSERIAL NOT NULL PRIMARY KEY,
    "username" VARCHAR(20) NOT NULL UNIQUE,
    "tenant_id" BIGINT,
    "email" VARCHAR(150) NOT NULL UNIQUE,
    "password" VARCHAR(255) NOT NULL,
    "first_name" VARCHAR(150) NOT NULL,
    "last_name" VARCHAR(150) NOT NULL,
    "is_active" BOOLEAN NOT NULL DEFAULT true,
    "is_super_admin" BOOLEAN NOT NULL DEFAULT false,
    "last_login" TIMESTAMP NOT NULL,
    "created_at" TIMESTAMP NOT NULL default current_timestamp,
    "updated_at" TIMESTAMP NOT NULL,
    "created_by" VARCHAR(100) NOT NULL,
    "updated_by" VARCHAR(100) NOT NULL
);

ALTER TABLE "users"
    ADD CONSTRAINT fk_user_tenant
        FOREIGN KEY ("tenant_id") REFERENCES "tenants"("id")
            ON DELETE CASCADE;


CREATE TABLE IF NOT EXISTS "roles" (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
    "tenant_id" BIGINT,
    "name" VARCHAR(20) NOT NULL,
    "description" TEXT,
    "isDefault" BOOLEAN NOT NULL DEFAULT false,
    "created_at" TIMESTAMP NOT NULL default current_timestamp,
    "updated_at" TIMESTAMP NOT NULL,
    "created_by" VARCHAR(100) NOT NULL,
    "updated_by" VARCHAR(100) NOT NULL
);

ALTER TABLE "roles"
    ADD CONSTRAINT fk_role_tenant
        FOREIGN KEY ("tenant_id") REFERENCES "tenants"("id")
            ON DELETE CASCADE ;

-- NOTE: here tenantId can be null meaning if it happens to be null, this will be consider as a role of the system itself
CREATE TABLE IF NOT EXISTS "user_roles" (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
    "user_id" BIGINT NOT NULL,
    "role_id" BIGINT NOT NULL,
    "tenant_id" BIGINT,
    "assigned_by" VARCHAR(100) NOT NULL,
    "assigned_at" TIMESTAMP NOT NULL DEFAULT current_timestamp,
    "expires_at" TIMESTAMP,
    UNIQUE("user_id", "role_id")
);

ALTER TABLE "user_roles"
    ADD CONSTRAINT fk_userrole_user
        FOREIGN KEY ("user_id") REFERENCES "users"("id")
            ON DELETE CASCADE;

ALTER TABLE "user_roles"
    ADD CONSTRAINT fk_userrole_role
        FOREIGN KEY ("role_id") REFERENCES "roles"("id")
            ON DELETE CASCADE;


CREATE TABLE IF NOT EXISTS "permissions" (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
    "code" VARCHAR(50) NOT NULL UNIQUE,
    "description" TEXT,
    "action" VARCHAR(50) NOT NULL,
    "resource" VARCHAR(100) NOT NULL,
    "module_id" BIGSERIAL NOT NULL,
    "created_at" TIMESTAMP NOT NULL default current_timestamp,
    "updated_at" TIMESTAMP NOT NULL,
    "created_by" VARCHAR(100) NOT NULL,
    "updated_by" VARCHAR(100) NOT NULL
);

ALTER TABLE "permissions"
    ADD CONSTRAINT fk_permission_module
        FOREIGN KEY("module_id") REFERENCES "modules"("id")
            ON DELETE CASCADE;


CREATE TABLE IF NOT EXISTS "role_permissions" (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
    "role_id" BIGINT NOT NULL,
    "permission_id" BIGINT NOT NULL,
    "granted_at" TIMESTAMP NOT NULL default current_timestamp,
    "granted_by" VARCHAR(100) NOT NULL,
    UNIQUE ("role_id", "permission_id")
);

ALTER TABLE "role_permissions"
    ADD CONSTRAINT fk_rolepermission_role
        FOREIGN KEY ("role_id") REFERENCES "roles"("id")
            ON DELETE CASCADE;

ALTER TABLE "role_permissions"
    ADD CONSTRAINT fk_rolepermission_permission
        FOREIGN KEY("permission_id") REFERENCES "permissions"("id")
            ON DELETE CASCADE;

CREATE TABLE IF NOT EXISTS "refresh_tokens" (
    "id" BIGSERIAL PRIMARY KEY NOT NULL,
    "token" TEXT NOT NULL,
    "user_id" BIGINT NOT NULL,
    "expiry_date" TIMESTAMP NOT NULL,
    "is_revoked" BOOLEAN NOT NULL DEFAULT false,
    "created_at" TIMESTAMP NOT NULL DEFAULT current_timestamp
);

ALTER TABLE "refresh_tokens"
    ADD CONSTRAINT fk_refreshtoken_user
        FOREIGN KEY("user_id") REFERENCES "users"("id")
            ON DELETE CASCADE;