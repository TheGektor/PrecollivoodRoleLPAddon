# PrecollivoodRoleLPAddon

**Автоматическая синхронизация ролей Discord и групп LuckPerms для Minecraft-серверов**

---

## Описание

Этот аддон для [LuckPerms](https://luckperms.net/) и [DiscordSRV](https://www.spigotmc.org/resources/discordsrv.18494/) позволяет автоматически выдавать Discord-роль игроку, если у него есть соответствующая группа в LuckPerms на Minecraft-сервере.

- Поддержка Paper/Spigot 1.20+
- Совместим с LuckPerms 5.x и DiscordSRV 1.29.0+
- Не требует отдельной установки JDA

---

## Возможности

- Привязка Minecraft-группы к Discord-роле через команду
- Автоматическая выдача/снятие Discord-ролей при изменении группы игрока
- Хранение связок в `roles.yml`
- Интеграция с DiscordSRV (использует привязку аккаунтов)

---

## Требования

- **LuckPerms** (плагин на сервере)
- **DiscordSRV** (плагин на сервере, версия 1.29.0 или новее)
- **Java 17+**
- **Maven** для сборки

---

## Установка

1. **Скачайте и установите LuckPerms и DiscordSRV** на сервер.
2. **Соберите плагин:**
   - Скачайте [DiscordSRV-1.29.0.jar](https://modrinth.com/plugin/discordsrv/versions) и поместите в папку `libs` внутри проекта.
   - Выполните:
     ```sh
     mvn clean package
     ```
   - Готовый jar-файл будет в папке `target/`.
3. **Скопируйте jar-файл плагина** в папку `plugins` вашего сервера.
4. **Перезапустите сервер.**

---

## Настройка и использование

### 1. Привязка Discord-ролей к группам

OP-игрок или админ вводит команду:

```
/ds-role add <название> <ID_роли_в_Discord>
```

**Пример:**
```
/ds-role add lord 123456789012345678
```

- После этого создаётся permission: `dsync.role.lord`
- В файл `plugins/PrecollivoodRoleLPAddon/roles.yml` добавляется связка

### 2. Настройка LuckPerms

Добавьте permission группе:
```
lp group lord permission set dsync.role.lord true
```

Теперь все игроки с группой `lord` будут автоматически получать Discord-роль с ID `123456789012345678` (и терять её при удалении группы).

---

## Как это работает

1. Плагин слушает события LuckPerms (выдача/снятие групп).
2. Проверяет, есть ли у игрока permission `dsync.role.<название>`.
3. Если есть — через DiscordSRV находит привязанный Discord-аккаунт.
4. Выдаёт или снимает роль на Discord-сервере.

---

## Получение ID Discord-ролей

1. Откройте Discord, включите режим разработчика (Настройки → Дополнительно → Режим разработчика).
2. ПКМ по нужной роли → "Копировать ID".

---

## Пример roles.yml

```yaml
lord: "123456789012345678"
admin: "234567890123456789"
```

---

## Права доступа

- Для команды `/ds-role` требуется permission: `dsync.admin`
- LuckPerms-группам выдаётся permission вида: `dsync.role.<название>`

---

## Советы для публикации на GitHub

- Добавьте этот README.md в корень репозитория
- Добавьте `.gitignore` для исключения `/target`, `/libs`, IDE-файлов и т.д.
- Пример `.gitignore`:
  ```gitignore
  /target/
  /libs/
  *.iml
  .idea/
  *.log
  *.class
  *.jar
  ```
- Добавьте лицензию (например, MIT или GPL-3.0)
- Оформите релиз с jar-файлом

---

## Обратная связь и поддержка

- Вопросы и баги — через Issues на GitHub
- Предложения — Pull Requests приветствуются!

---

**Автор:** [Antag0nis1](https://github.com/TheGektor) 