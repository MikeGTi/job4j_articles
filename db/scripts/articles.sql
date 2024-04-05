create cached table if not exists articles (
    id serial primary key,
    text text
);

set files write delay 10;
