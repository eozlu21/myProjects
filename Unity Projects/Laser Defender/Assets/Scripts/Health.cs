using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Health : MonoBehaviour
{
    [SerializeField] private int health = 50;

    private void OnTriggerEnter2D(Collider2D other)
    {
        DamageDealer damageDealer = other.GetComponent<DamageDealer>();
        if (damageDealer == null)
        {
            return;
        }
        TakeDamage(damageDealer.GetDamage());
        damageDealer.Hit();
    }

    private void TakeDamage(int damage)
    {
        
        health -= damage;
        if (health > 0)
        {
            return;
        }

        Destroy(gameObject);
    }
}