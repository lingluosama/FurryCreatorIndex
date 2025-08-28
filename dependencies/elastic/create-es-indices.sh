#!/bin/bash

# Elasticsearch 主机地址
ES_HOST="http://localhost:9200"

# 等待 Elasticsearch 可用
echo "Waiting for Elasticsearch to be available..."
until curl -s "$ES_HOST" > /dev/null; do
    sleep 5
done

echo "Creating Elasticsearch indices with proper mappings..."

# 创建 artist 索引和映射
curl -X PUT "$ES_HOST/debezium-v3-mysql-wiki-v3.testdb.artist" -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "properties": {
      "id": { "type": "long" },
      "name": {
        "type": "text",
        "fields": {
          "keyword": { "type": "keyword", "ignore_above": 256 }
        }
      },
      "bio": { "type": "text" },
      "avatar_url": {
        "type": "keyword",
        "ignore_above": 512
      },
      "art_style": { "type": "text" },
      "social_links": {
        "type": "text",
        "fields": {
          "keyword": { "type": "keyword", "ignore_above": 512 }
        }
      },
      "website_url": {
        "type": "keyword",
        "ignore_above": 512
      },
      "status": {
        "type": "keyword",
        "ignore_above": 50
      },
      "created_at": { "type": "date" },
      "updated_at": { "type": "date" }
    }
  }
}
'

# 创建 artwork 索引和映射
curl -X PUT "$ES_HOST/debezium-v3-mysql-wiki-v3.testdb.artwork" -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "properties": {
      "id": { "type": "long" },
      "title": {
        "type": "text",
        "fields": {
          "keyword": { "type": "keyword", "ignore_above": 256 }
        }
      },
      "artist_id": { "type": "long" },
      "description": { "type": "text" },
      "image_urls": {
        "type": "text",
        "fields": {
          "keyword": { "type": "keyword", "ignore_above": 512 }
        }
      },
      "created_at": { "type": "date" },
      "updated_at": { "type": "date" }
    }
  }
}
'

# 创建 creator 索引和映射
curl -X PUT "$ES_HOST/debezium-v3-mysql-wiki-v3.testdb.creator" -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "properties": {
      "id": { "type": "long" },
      "name": {
        "type": "text",
        "fields": {
          "keyword": { "type": "keyword", "ignore_above": 256 }
        }
      },
      "bio": { "type": "text" },
      "avatar_url": {
        "type": "keyword",
        "ignore_above": 512
      },
      "social_links": {
        "type": "text",
        "fields": {
          "keyword": { "type": "keyword", "ignore_above": 512 }
        }
      },
      "website_url": {
        "type": "keyword",
        "ignore_above": 512
      },
      "status": {
        "type": "keyword",
        "ignore_above": 50
      },
      "created_at": { "type": "date" },
      "updated_at": { "type": "date" }
    }
  }
}
'

# 创建 game 索引和映射
curl -X PUT "$ES_HOST/debezium-v3-mysql-wiki-v3.testdb.game" -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "properties": {
      "id": { "type": "long" },
      "title": {
        "type": "text",
        "fields": {
          "keyword": { "type": "keyword", "ignore_above": 256 }
        }
      },
      "platform": {
        "type": "keyword",
        "ignore_above": 255
      },
      "genre": {
        "type": "keyword",
        "ignore_above": 255
      },
      "release_date": { "type": "date" },
      "developer_id": { "type": "long" },
      "publisher_id": { "type": "long" },
      "description": { "type": "text" },
      "cover_image_url": {
        "type": "keyword",
        "ignore_above": 512
      },
      "official_website_url": {
        "type": "keyword",
        "ignore_above": 512
      },
      "created_at": { "type": "date" },
      "updated_at": { "type": "date" }
    }
  }
}
'

# 创建 literature_work 索引和映射
curl -X PUT "$ES_HOST/debezium-v3-mysql-wiki-v3.testdb.literature_work" -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "properties": {
      "id": { "type": "long" },
      "title": {
        "type": "text",
        "fields": {
          "keyword": { "type": "keyword", "ignore_above": 256 }
        }
      },
      "type": {
        "type": "keyword",
        "ignore_above": 50
      },
      "author_id": { "type": "long" },
      "illustrator_id": { "type": "long" },
      "publisher_id": { "type": "long" },
      "release_date": { "type": "date" },
      "description": { "type": "text" },
      "cover_image_url": {
        "type": "keyword",
        "ignore_above": 512
      },
      "official_website_url": {
        "type": "keyword",
        "ignore_above": 512
      },
      "created_at": { "type": "date" },
      "updated_at": { "type": "date" }
    }
  }
}
'

# 创建 media_work 索引和映射
curl -X PUT "$ES_HOST/debezium-v3-mysql-wiki-v3.testdb.media_work" -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "properties": {
      "id": { "type": "long" },
      "title": {
        "type": "text",
        "fields": {
          "keyword": { "type": "keyword", "ignore_above": 256 }
        }
      },
      "type": {
        "type": "keyword",
        "ignore_above": 50
      },
      "release_date": { "type": "date" },
      "director_id": { "type": "long" },
      "studio_id": { "type": "long" },
      "description": { "type": "text" },
      "cover_image_url": {
        "type": "keyword",
        "ignore_above": 512
      },
      "official_website_url": {
        "type": "keyword",
        "ignore_above": 512
      },
      "created_at": { "type": "date" },
      "updated_at": { "type": "date" }
    }
  }
}
'

# 创建 wiki_entry 索引和映射
curl -X PUT "$ES_HOST/debezium-v3-mysql-wiki-v3.testdb.wiki_entry" -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "properties": {
      "id": { "type": "long" },
      "title": {
        "type": "text",
        "fields": {
          "keyword": { "type": "keyword", "ignore_above": 256 }
        }
      },
      "slug": {
        "type": "keyword",
        "ignore_above": 255
      },
      "category_id": { "type": "long" },
      "content": { "type": "text" },
      "cover_image_url": {
        "type": "keyword",
        "ignore_above": 512
      },
      "status": {
        "type": "keyword",
        "ignore_above": 50
      },
      "view_count": { "type": "long" },
      "created_by": { "type": "long" },
      "created_at": { "type": "date" },
      "updated_by": { "type": "long" },
      "updated_at": { "type": "date" },
      "is_deleted": { "type": "boolean" }
    }
  }
}
'

echo "Elasticsearch indices creation completed."