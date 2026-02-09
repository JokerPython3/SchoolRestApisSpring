# جان بيه خلل صلحته ونشرته

# Rest Api 
# لتطبيق مدرسي فكرته انو مدرس يدز واجبات بي قنوات وطلاب يشوفها فقط الي عندهم ROLE TEACHER يكدرون يدززون رسايل
# مامسوي ني api تحكم بي انشاء قنوات  
# تضيف قنوات عن طريق قاعده بياناتك ال id في اول جدول هو ايدي قناه واكو ايدي ثاني سمه classId او class هو صف مثلا ثالث 
# واكو classABC هذا شعبه 
# غير اعدادات قاعده بيانات حسب قاعد بيانات الي عندك بي application.properties
# وشارح بل ملف شلون تقير
# استخدمت swagger حتى يسوي doc for apis 
# من تشغلون تروحن لي
```http http://localhost:8000/swagger-ui/index.html ```
# هذا بيه doc لي apis 
# بورت سيرفر 8000 تكدرون تقيروه
# download repo use git tools
```bash 
https://github.com/JokerPython3/SchoolRestApisSpring
```
# ردت اسوي فرونت ايند بس تعب والله تكدرون تستخدمون V0.dev حتى يسويلكم فرونت ايند واشرحوله عن apis و webscoket شون يتعامل وياهن ودزوله ريسبونس واكو apis ماموجوده بيه doc هاي apis من نوع websocket تلكاوه بي مجلد Controlre/AuthControlre/WebSocket 
# بي application.proprties اذا تستخدم قاعده بيانات غير mysql لازم تحمل driver مالته بل pom.xml وبعدما تحطه بل دبندنسي اكتب هذا بل تيرمينال
```bash
mvn clean install
```
# ولازم تحملون maven

# doc apis =>
```json
{
  "openapi": "3.0.1",
  "info": {
    "title": "OpenAPI definition",
    "version": "v0"
  },
  "servers": [
    {
      "url": "http://localhost:8000",
      "description": "Generated server url"
    }
  ],
  "paths": {
    "/v1/authroztion/access/atro/verify/refresh/": {
      "post": {
        "tags": [
          "acc"
        ],
        "operationId": "verifyRefresh",
        "parameters": [
          {
            "name": "token",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "object",
                  "additionalProperties": {
                    "type": "object"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/v1/authroztion/access/atro/verify/access/": {
      "post": {
        "tags": [
          "acc"
        ],
        "operationId": "verifyAccess",
        "parameters": [
          {
            "name": "Authorization",
            "in": "header",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "object",
                  "additionalProperties": {
                    "type": "object"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/v1/authroztion/access/atro/user/account/logout/": {
      "post": {
        "tags": [
          "acc"
        ],
        "operationId": "logoutUser",
        "parameters": [
          {
            "name": "Authorization",
            "in": "header",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "object",
                  "additionalProperties": {
                    "type": "object"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/v1/authroztion/access/atro/get/user/from/access/": {
      "post": {
        "tags": [
          "acc"
        ],
        "operationId": "getUserFromAccess",
        "parameters": [
          {
            "name": "Authorization",
            "in": "header",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "object",
                  "additionalProperties": {
                    "type": "object"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/v1/authroztion/access/atro/blacklist/refresh/": {
      "post": {
        "tags": [
          "acc"
        ],
        "operationId": "blacklistRefresh",
        "parameters": [
          {
            "name": "token",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "object",
                  "additionalProperties": {
                    "type": "object"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/v1/authroztion/access/atro/blacklist/access/": {
      "post": {
        "tags": [
          "acc"
        ],
        "operationId": "blacklistAccess",
        "parameters": [
          {
            "name": "Authorization",
            "in": "header",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "object",
                  "additionalProperties": {
                    "type": "object"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/v1/authroztion/access/atro/access/from/refresh/": {
      "post": {
        "tags": [
          "acc"
        ],
        "operationId": "accessFromRefresh",
        "parameters": [
          {
            "name": "token",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          },
          {
            "name": "Authorization",
            "in": "header",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "object",
                  "additionalProperties": {
                    "type": "object"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/register/": {
      "post": {
        "tags": [
          "specfic"
        ],
        "operationId": "RegisterAtro",
        "parameters": [
          {
            "name": "username",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          },
          {
            "name": "email",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          },
          {
            "name": "password",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          },
          {
            "name": "classId",
            "in": "query",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          },
          {
            "name": "ClassABC",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "object",
                  "additionalProperties": {
                    "type": "object"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/login/": {
      "post": {
        "tags": [
          "specfic"
        ],
        "operationId": "LoginAtro",
        "parameters": [
          {
            "name": "username",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          },
          {
            "name": "password",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          },
          {
            "name": "classId",
            "in": "query",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          },
          {
            "name": "ClassABC",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "object",
                  "additionalProperties": {
                    "type": "object"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/api/messages/upload": {
      "post": {
        "tags": [
          "message-rest"
        ],
        "operationId": "uploadMessage",
        "parameters": [
          {
            "name": "text",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          },
          {
            "name": "sender",
            "in": "query",
            "required": true,
            "schema": {
              "type": "string"
            }
          },
          {
            "name": "channelId",
            "in": "query",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          },
          {
            "name": "Authorization",
            "in": "header",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ],
        "requestBody": {
          "content": {
            "application/json": {
              "schema": {
                "type": "object",
                "properties": {
                  "image": {
                    "type": "string",
                    "format": "binary"
                  }
                }
              }
            }
          }
        },
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/MessgessssK"
                }
              }
            }
          }
        }
      }
    },
    "/api/channesl": {
      "post": {
        "tags": [
          "web-socket-controllre"
        ],
        "operationId": "createChannel",
        "parameters": [
          {
            "name": "channel",
            "in": "query",
            "required": true,
            "schema": {
              "$ref": "#/components/schemas/Channels"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "$ref": "#/components/schemas/Channels"
                }
              }
            }
          }
        }
      }
    },
    "/api/messages/{channelId}": {
      "get": {
        "tags": [
          "message-rest"
        ],
        "operationId": "history",
        "parameters": [
          {
            "name": "channelId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "array",
                  "items": {
                    "$ref": "#/components/schemas/MessgessssK"
                  }
                }
              }
            }
          }
        }
      }
    },
    "/api/messages/join/{channelId}/{userId}": {
      "get": {
        "tags": [
          "message-rest"
        ],
        "operationId": "JoinesChann",
        "parameters": [
          {
            "name": "channelId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          },
          {
            "name": "userId",
            "in": "path",
            "required": true,
            "schema": {
              "type": "integer",
              "format": "int64"
            }
          }
        ],
        "responses": {
          "200": {
            "description": "OK",
            "content": {
              "*/*": {
                "schema": {
                  "type": "object",
                  "additionalProperties": {
                    "type": "object"
                  }
                }
              }
            }
          }
        }
      }
    }
  },
  "components": {
    "schemas": {
      "Channels": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "name": {
            "type": "string"
          },
          "description": {
            "type": "string"
          },
          "now": {
            "$ref": "#/components/schemas/LocalTime"
          },
          "classiD": {
            "type": "integer",
            "format": "int64"
          },
          "classABC": {
            "type": "string"
          }
        }
      },
      "LocalTime": {
        "type": "object",
        "properties": {
          "hour": {
            "type": "integer",
            "format": "int32"
          },
          "minute": {
            "type": "integer",
            "format": "int32"
          },
          "second": {
            "type": "integer",
            "format": "int32"
          },
          "nano": {
            "type": "integer",
            "format": "int32"
          }
        }
      },
      "MessgessssK": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "channel": {
            "$ref": "#/components/schemas/Channels"
          },
          "content": {
            "type": "string"
          },
          "sender": {
            "type": "string"
          },
          "textImages": {
            "type": "string"
          },
          "now": {
            "$ref": "#/components/schemas/LocalTime"
          },
          "imagePath": {
            "type": "string"
          },
          "image": {
            "type": "array",
            "items": {
              "type": "string",
              "format": "byte"
            }
          },
          "user": {
            "$ref": "#/components/schemas/User"
          }
        }
      },
      "User": {
        "type": "object",
        "properties": {
          "id": {
            "type": "integer",
            "format": "int64"
          },
          "username": {
            "type": "string"
          },
          "password": {
            "type": "string"
          },
          "email": {
            "type": "string"
          },
          "name": {
            "type": "string"
          },
          "role": {
            "type": "string"
          },
          "classId": {
            "type": "integer",
            "format": "int64"
          },
          "classABC": {
            "type": "string"
          },
          "channels": {
            "type": "array",
            "items": {
              "$ref": "#/components/schemas/Channels"
            }
          },
          "timestamp": {
            "type": "string",
            "format": "date-time"
          }
        }
      }
    }
  }
}```
