```json
{
  "icon": "minecraft:clock",
  "title": "Recharging",
  "category": "victus:basics"
}
```

When aspects break, they need to recharge before they can activate again. This process happens sequentially from left to
right and is visualized by a {gold}golden outline{} on your health bar. This outline covers all hearts with an aspect on
them and is shown as long as *any* aspect is not fully recharged.

;;;;;

While designing your aspect setup, it is important to keep in mind that a recharging aspect will **completely** block
all the ones after it from recharging, so you probably shouldn't stuff a really slow one far down your health bar.


The recharge duration of each aspect is show in its {gold}tooltip{}. If you feel your aspects recharge too slowly, a
{gold}Potion of Resurgence{} may be of interest.